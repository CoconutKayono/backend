package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.vo.response.CommentVO;

import java.util.List;


public interface CommentService extends IService<Comment> {
    List<CommentVO> listCommentVOs();

    List<CommentVO> listCommentVOs(Integer id);
}
