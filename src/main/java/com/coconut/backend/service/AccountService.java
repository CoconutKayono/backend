package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.Account;
import com.coconut.backend.entity.vo.request.EmailRegisterVO;
import com.coconut.backend.entity.vo.request.EmailResetVO;
import com.coconut.backend.entity.vo.request.EmailVerifyCodeVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String text);

    String emailVerifyCode(EmailVerifyCodeVO vo, String ip);

    String registerEmailAccount(EmailRegisterVO vo);

    String resetEmailPassword(EmailResetVO vo);
}
