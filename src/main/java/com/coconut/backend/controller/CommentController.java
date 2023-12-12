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
@Tag(name="Comment参数")
public class CommentController {
    @Resource
    CommentService commentService;

    @Operation(summary = "游客:查看所有评论")
    @GetMapping("/guest/list")
    public RestBean<List<CommentVO>> guestQueryComments() {
        List<CommentVO> commentVOs = commentService.listCommentVOs();
        return commentVOs != null ? RestBean.success(commentVOs) : RestBean.failure(404, "暂无任何评论");
    }

    @Operation(summary = "用户:查看所有评论")
    @GetMapping("/loggedIn/list")
    public RestBean<List<CommentVO>> loggedQueryComments(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        List<CommentVO> commentVOs = commentService.listCommentVOs(userId);
        return commentVOs != null ? RestBean.success(commentVOs) : RestBean.failure(404, "暂无任何评论");
    }

    @Operation(summary = "用户:查看所有评论")
    @Parameters({
            @Parameter(name = "Comment",description = "评论",in = ParameterIn.DEFAULT),
    })
    @PostMapping("/loggedIn/post")
    public RestBean<String> addComment(@RequestBody Comment comment) {
        String message = commentService.saveComment(comment);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }
}
