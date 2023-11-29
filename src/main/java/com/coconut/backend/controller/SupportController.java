package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.LikeVO;
import com.coconut.backend.service.SupportService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.function.Supplier;

@Controller("/api/support")
public class SupportController {
    @Resource
    SupportService supportService;

    @PostMapping("/likeNote")
    public RestBean<Void> likeNote(@RequestBody @Valid LikeVO vo) {
        return this.handleMessage(() -> supportService.likeNote(vo));
    }

    @DeleteMapping("/cancelNote")
    public RestBean<Void> cancelNote(@RequestBody @Valid LikeVO vo) {
        return this.handleMessage(() -> supportService.unlikeNote(vo));
    }

    @PostMapping("/likeComment")
    public RestBean<Void> likeComment(@RequestBody @Valid LikeVO vo) {
        return this.handleMessage(() -> supportService.likeComment(vo));
    }

    @DeleteMapping("/cancelComment")
    public RestBean<Void> cancelComment(@RequestBody @Valid LikeVO vo) {
        return this.handleMessage(() -> supportService.unlikeComment(vo));
    }

    private RestBean<Void> handleMessage(Supplier<String> action) {
        String message = action.get();
        return message == null ? RestBean.success() : RestBean.failure(520, message);
    }
}
