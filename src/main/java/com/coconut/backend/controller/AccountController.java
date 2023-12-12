package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.response.UserVO;
import com.coconut.backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
@Tag(name="AccountController")
public class AccountController {
    @Resource
    AccountService accountService;

    @Operation(summary = "用户:获取User对象")
    @Parameters({
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @GetMapping("/loggedIn/userVO")
    public RestBean<UserVO> queryUserById(HttpServletRequest request) {
        UserVO userVO = accountService.getUserVOById((Integer) request.getAttribute("userId"));
        return RestBean.success(userVO);
    }

}
