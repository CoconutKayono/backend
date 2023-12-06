package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.dto.Account;
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
    @GetMapping("/info")
    public RestBean<UserVO> userInfo(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        Account account = accountService.getById(id);
        UserVO userVO = UserVO.newInstance(account);
        return RestBean.success(userVO);
    }

}
