package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Account;
import com.coconut.backend.entity.vo.request.EmailRegisterVO;
import com.coconut.backend.entity.vo.request.EmailResetVO;
import com.coconut.backend.entity.vo.request.EmailVerifyCodeVO;
import com.coconut.backend.entity.vo.response.UserVO;
import com.coconut.backend.mapper.AccountMapper;
import com.coconut.backend.service.AccountService;
import com.coconut.backend.utlis.Const;
import com.coconut.backend.utlis.FlowUtils;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {
    @Value("${spring.mail.limitTime}")
    Integer limitTime;
    @Resource
    FlowUtils flowUtils;
    @Resource
    AccountMapper accountMapper;
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    PasswordEncoder passwordEncoder;

    /**
     * 使用用户名或邮箱,密码登录
     *
     * @param text String 用户名或邮箱
     */
    @Override
    public UserDetails loadUserByUsername(String text) throws UsernameNotFoundException {
        Account account = findAccountByNameOrEmail(text);
        if (account == null) throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(text)
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    @Override
    public String emailVerifyCode(EmailVerifyCodeVO vo, String ip) {
        String type = vo.type();
        String email = vo.email();
        synchronized (ip.intern()) {
            if (!this.verifyLimit(ip)) return "请求频繁,请稍后再试。";
            Random random = new Random();
            Integer code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of("type", type, "email", email, "code", code);
            rabbitTemplate.convertAndSend("amq.direct", "my-mail", data);
            stringRedisTemplate.opsForValue()
                    .set(Const.VERIFY_MAIL_DATA + email, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        }
    }

    @Override
    public String registerEmailAccount(EmailRegisterVO vo) {
        String email = vo.email();
        String username = vo.username();
        String key = Const.VERIFY_MAIL_DATA + email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null) return "请先获取验证码";
        if (!code.equals(vo.code())) return "验证码输入错误,请重新输入";
        if (existsAccountByEmail(email)) return "此电子邮箱已被注册";
        if (existsAccountByUsername(username)) return "此用户名已被注册";
        String password = passwordEncoder.encode(vo.password());
        Account account = new Account.Builder()
                .id(null)
                .username(username)
                .password(password)
                .email(email)
                .avatar(null)
                .experience(0)
                .role("user")
                .registerTime(LocalDateTime.now())
                .build();
        if (this.save(account)) {
            stringRedisTemplate.delete(key);
            return null;
        } else return "内部错误,请联系管理员";
    }

    @Override
    public String resetEmailPassword(EmailResetVO vo) {
        String email = vo.email();
        String key = Const.VERIFY_MAIL_DATA + email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null) return "请先获取验证码";
        if (!code.equals(vo.code())) return "验证码输入错误,请重新输入";
        if (!existsAccountByEmail(email)) return "该电子邮箱未注册";
        String password = passwordEncoder.encode(vo.password());
        boolean update = this.lambdaUpdate().eq(Account::getEmail, email).set(Account::getPassword, password).update();
        if (update) stringRedisTemplate.delete(key);
        return update ? null : "内部错误,请联系管理员";
    }

    @Override
    public UserVO getUserVOById(Integer id) {
        Account account = accountMapper.selectById(id);
        return UserVO.newInstance(account);
    }


    public Account findAccountByNameOrEmail(String text) {
        return this.lambdaQuery()
                .eq(Account::getUsername, text)
                .or()
                .eq(Account::getEmail, text)
                .one();
    }

    private boolean existsAccountByEmail(String email) {
        return this.exists(new LambdaQueryWrapper<Account>().eq(Account::getEmail, email));
    }

    private boolean existsAccountByUsername(String username) {
        return this.exists(new LambdaQueryWrapper<Account>().eq(Account::getUsername, username));
    }

    private Boolean verifyLimit(String ip) {
        String key = Const.VERIFY_MAIL_LIMIT + ip;
        System.out.println(key);
        return flowUtils.limitOnceCheck(key, limitTime);
    }
}
