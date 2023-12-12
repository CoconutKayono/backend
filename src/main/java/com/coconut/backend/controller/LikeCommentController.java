package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.LikeCommentVO;
import com.coconut.backend.entity.vo.response.LikeVO;
import com.coconut.backend.service.LikeCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/likeComment")
@Tag(name="LikeCommentController")
public class LikeCommentController {
    @Resource
    LikeCommentService likeCommentService;

    @Operation(summary = "用户:点赞")
    @Parameters({
            @Parameter(name = "LikeCommentVO",description = "评论点赞视图",in = ParameterIn.DEFAULT),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @PostMapping("/loggedIn/like")
    public RestBean<LikeVO> like(@RequestBody @Valid LikeCommentVO vo) {
        return this.handleMessage(() -> likeCommentService.like(vo));
    }

    @Operation(summary = "用户:取消点赞")
    @Parameters({
            @Parameter(name = "LikeCommentVO",description = "评论点赞视图",in = ParameterIn.DEFAULT),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @PostMapping("/loggedIn/unlike")
    public RestBean<LikeVO> unlike(@RequestBody @Valid LikeCommentVO vo) {
        return this.handleMessage(() -> likeCommentService.unlike(vo));
    }


    private RestBean<LikeVO> handleMessage(Supplier<LikeVO> action) {
        LikeVO data = action.get();
        return data != null ? RestBean.success(data) : RestBean.failure(520, "内部错误,请联系管理员");
    }
}
