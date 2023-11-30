package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.coconut.backend.entity.vo.response.LikeVO;
import com.coconut.backend.service.LikeNoteService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping("/api/likeNote")
public class LikeNoteController {
    @Resource
    LikeNoteService likeNoteService;

    @PostMapping("/like")
    public RestBean<LikeVO> like(@RequestBody @Valid LikeNoteVO vo) {
        return this.handleMessage(() -> likeNoteService.likeNote(vo));
    }

    @PostMapping("/unlike")
    public RestBean<LikeVO> unlike(@RequestBody @Valid LikeNoteVO vo) {
        return this.handleMessage(() -> likeNoteService.unlikeNote(vo));
    }


    private RestBean<LikeVO> handleMessage(Supplier<LikeVO> action) {
        LikeVO data = action.get();
        return data != null ? RestBean.success(data) : RestBean.failure(520, "内部错误,请联系管理员");
    }
}
