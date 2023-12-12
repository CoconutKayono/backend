package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.vo.response.CommentVO;
import com.coconut.backend.service.CommentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    @GetMapping("/list")
    public RestBean<List<CommentVO>> list(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        List<CommentVO> commentVOs;
        if (id == null) {
            commentVOs = commentService.listCommentVOs();
        } else {
            commentVOs = commentService.listCommentVOs(id);
        }
        if (commentVOs != null) return RestBean.success(commentVOs);
        else return RestBean.failure(404, "暂无任何评论");
    }

    @PostMapping("/post")
    public RestBean<String> postComment(@RequestBody Comment comment) {
        String message = commentService.saveComment(comment);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }
}
