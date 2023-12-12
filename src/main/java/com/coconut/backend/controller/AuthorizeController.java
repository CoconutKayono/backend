package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.EmailRegisterVO;
import com.coconut.backend.entity.vo.request.EmailResetVO;
import com.coconut.backend.entity.vo.request.EmailVerifyCodeVO;
import com.coconut.backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Authorize参数")
public class AuthorizeController {
    @Resource
    AccountService accountService;

    /**
     * 请求验证码
     *
     * @param vo             邮箱验证码视图
     * @param servletRequest 用于获取Ip地址,来实现限流
     * @return 是否成功获取验证码
     */
    @Operation(summary = "无:获取邮箱验证码")
    @Parameters({
            @Parameter(name = "EmailVerifyCodeVO",description = "通过邮箱验证视图",in = ParameterIn.DEFAULT),
    })
    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@Valid EmailVerifyCodeVO vo,
                                        HttpServletRequest servletRequest) {
        return this.handleMessage(() ->
                accountService.emailVerifyCode(vo, servletRequest.getRemoteAddr())
        );
    }

    @Operation(summary = "无:注册")
    @Parameters({
            @Parameter(name = "EmailRegisterVO",description = "通过邮件注册视图",in = ParameterIn.DEFAULT),
    })
    @PostMapping("/register")
    public RestBean<Void> register(@RequestBody @Valid EmailRegisterVO vo) {
        return this.handleMessage(() -> accountService.registerEmailAccount(vo));
    }

    @Operation(summary = "无:重置密码")
    @Parameters({
            @Parameter(name = "EmailResetVO",description = "通过邮箱重置视图",in = ParameterIn.DEFAULT),
    })
    @PostMapping("/reset")
    public RestBean<Void> reset(@RequestBody @Valid EmailResetVO vo) {
        return this.handleMessage(() -> accountService.resetEmailPassword(vo));
    }

    private RestBean<Void> handleMessage(Supplier<String> supplier) {
        String message = supplier.get();
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }
}
