package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.mapper.CommentMapper;
import com.coconut.backend.service.CommentService;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

}




