package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.coconut.backend.entity.vo.response.LikeVO;
import com.coconut.backend.service.LikeNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="LikeNoteController")
public class LikeNoteController {
    @Resource
    LikeNoteService likeNoteService;

    @Operation(summary = "用户:给博客点赞")
    @Parameters({
            @Parameter(name = "LikeNoteVO",description = "博客点赞视图",in = ParameterIn.DEFAULT),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @PostMapping("/loggedIn/like")
    public RestBean<LikeVO> like(@RequestBody @Valid LikeNoteVO vo) {
        return this.handleMessage(() -> likeNoteService.like(vo));
    }

    @Operation(summary = "用户:给博客取消点赞")
    @Parameters({
            @Parameter(name = "LikeNoteVO",description = "博客点赞视图",in = ParameterIn.DEFAULT),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @PostMapping("/loggedIn/unlike")
    public RestBean<LikeVO> unlike(@RequestBody @Valid LikeNoteVO vo) {
        return this.handleMessage(() -> likeNoteService.unlike(vo));
    }


    private RestBean<LikeVO> handleMessage(Supplier<LikeVO> action) {
        LikeVO data = action.get();
        return data != null ? RestBean.success(data) : RestBean.failure(520, "内部错误,请联系管理员");
    }
}
