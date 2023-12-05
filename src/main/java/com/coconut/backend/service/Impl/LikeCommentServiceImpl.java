package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.dto.LikeComment;
import com.coconut.backend.entity.vo.request.LikeCommentVO;
import com.coconut.backend.entity.vo.response.LikeVO;
import com.coconut.backend.mapper.LikeCommentMapper;
import com.coconut.backend.service.CommentService;
import com.coconut.backend.service.LikeCommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeCommentServiceImpl extends ServiceImpl<LikeCommentMapper, LikeComment>
        implements LikeCommentService {
    @Resource
    LikeCommentMapper likeCommentMapper;
    @Resource
    CommentService commentService;

    @Transactional
    @Override
    public LikeVO like(LikeCommentVO vo) {
        if (this.hasLiked(vo)) {
            return this.unlike(vo);
        } else {
            likeCommentMapper.insert(LikeComment.newInstance(vo));

            Comment comment = commentService.getById(vo.commentId());
            comment.increaseLikes();
            commentService.updateById(comment);
            return new LikeVO(comment.getSupport(), true);
        }
    }

    @Transactional
    @Override
    public LikeVO unlike(LikeCommentVO vo) {
        if (!this.hasLiked(vo)) {
            return this.like(vo);
        } else {
            likeCommentMapper.delete(new LambdaQueryWrapper<LikeComment>()
                    .eq(LikeComment::getUserId, vo.userId())
                    .eq(LikeComment::getNoteId, vo.noteId())
                    .eq(LikeComment::getCommentId, vo.commentId()));

            Comment comment = commentService.getById(vo.noteId());
            comment.decrementLike();
            commentService.updateById(comment);
            return new LikeVO(comment.getSupport(), false);
        }
    }

    private Boolean hasLiked(LikeCommentVO vo) {
        LikeComment likeComment = likeCommentMapper.selectOne(new LambdaQueryWrapper<LikeComment>()
                .eq(LikeComment::getUserId, vo.userId())
                .eq(LikeComment::getNoteId, vo.noteId())
                .eq(LikeComment::getCommentId, vo.commentId()));
        // 返回是否点赞,是为true,否为false
        return likeComment != null;
    }
}




