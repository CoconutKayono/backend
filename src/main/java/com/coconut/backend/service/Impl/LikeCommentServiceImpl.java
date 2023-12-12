package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.dto.LikeComment;
import com.coconut.backend.entity.vo.request.LikeCommentVO;
import com.coconut.backend.entity.vo.response.LikeVO;
import com.coconut.backend.mapper.CommentMapper;
import com.coconut.backend.mapper.LikeCommentMapper;
import com.coconut.backend.service.LikeCommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LikeCommentServiceImpl extends ServiceImpl<LikeCommentMapper, LikeComment>
        implements LikeCommentService {
    @Resource
    LikeCommentMapper likeCommentMapper;
    @Resource
    CommentMapper commentMapper;

    @Transactional
    @Override
    public LikeVO like(LikeCommentVO vo) {
        if (this.hasLiked(vo)) {
            return null;
        } else {
            likeCommentMapper.insert(new LikeComment.Builder()
                    .id(null)
                    .userId(vo.userId())
                    .noteId(vo.noteId())
                    .commentId(vo.commentId())
                    .createdTime(LocalDateTime.now())
                    .build());
            Comment comment = commentMapper.selectById(vo.commentId());
            comment.increaseLikes();
            commentMapper.updateById(comment);
            return new LikeVO(comment.getSupport(), true);
        }
    }

    @Transactional
    @Override
    public LikeVO unlike(LikeCommentVO vo) {
        if (!this.hasLiked(vo)) {
            return null;
        } else {
            likeCommentMapper.delete(Wrappers.<LikeComment>lambdaQuery()
                    .eq(LikeComment::getUserId, vo.userId())
                    .eq(LikeComment::getNoteId, vo.noteId())
                    .eq(LikeComment::getCommentId, vo.commentId()));

            Comment comment = commentMapper.selectById(vo.noteId());
            comment.decrementLike();
            commentMapper.updateById(comment);
            return new LikeVO(comment.getSupport(), false);
        }
    }

    private Boolean hasLiked(LikeCommentVO vo) {
        LikeComment likeComment = likeCommentMapper.selectOne(Wrappers.<LikeComment>lambdaQuery()
                .eq(LikeComment::getUserId, vo.userId())
                .eq(LikeComment::getNoteId, vo.noteId())
                .eq(LikeComment::getCommentId, vo.commentId()));
        // 返回是否点赞,是为true,否为false
        return likeComment != null;
    }
}




