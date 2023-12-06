package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.response.UserVO;
import com.coconut.backend.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class AccountController {
    @Resource
    AccountService accountService;


    /**
     * 需要登录权限
     */
    @GetMapping("/userVO")
    public RestBean<UserVO> getUserVO(HttpServletRequest request) {
        UserVO userVO = accountService.getUserVOById((Integer) request.getAttribute("id"));
        return RestBean.success(userVO);
    }

}
