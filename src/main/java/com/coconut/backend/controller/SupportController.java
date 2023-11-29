package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.LikeVO;
import com.coconut.backend.service.SupportService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/api/support")
public class SupportController {
    @Resource
    SupportService supportService;

    @PostMapping("/like")
    public RestBean<Void> support(LikeVO likeVO) {
        return null;
    }
}
