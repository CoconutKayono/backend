package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.vo.response.CommentVO;
import com.coconut.backend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "CommentController")
public class CommentController {
    @Resource
    CommentService commentService;

    @Operation(summary = "游客:获取所有评论")
    @GetMapping("/guest/list")
    public RestBean<List<CommentVO>> guestQueryComments() {
        List<CommentVO> commentVOs = commentService.listCommentVOs();
        return commentVOs != null ? RestBean.success(commentVOs) : RestBean.failure(404, "暂无任何评论");
    }

    @Operation(summary = "用户:获取所有评论(携带用户与评论有关的信息)")
    @Parameters({
            @Parameter(name = "token", description = "请求token", required = true, in = ParameterIn.HEADER),
    })
    @GetMapping("/loggedIn/list")
    public RestBean<List<CommentVO>> loggedQueryComments(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        List<CommentVO> commentVOs = commentService.listCommentVOs(userId);
        return commentVOs != null ? RestBean.success(commentVOs) : RestBean.failure(404, "暂无任何评论");
    }

    @Operation(summary = "用户:上传评论")
    @Parameters({
            @Parameter(name = "Comment", description = "评论", in = ParameterIn.DEFAULT),
            @Parameter(name = "token", description = "请求token", required = true, in = ParameterIn.HEADER),
    })
    @PostMapping("/loggedIn/post")
    public RestBean<String> addComment(@RequestBody Comment comment) {
        String message = commentService.saveComment(comment);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }
}
