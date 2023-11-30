package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.dto.Note;
import com.coconut.backend.entity.dto.Support;
import com.coconut.backend.entity.vo.request.LikeVO;
import com.coconut.backend.mapper.SupportMapper;
import com.coconut.backend.service.CommentService;
import com.coconut.backend.service.NoteService;
import com.coconut.backend.service.SupportService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SupportServiceImpl extends ServiceImpl<SupportMapper, Support>
    implements SupportService{
    @Resource
    SupportMapper supportMapper;
    @Resource
    CommentService commentService;
    @Resource
    NoteService noteService;
    @Transactional
    @Override
    public String likeNote(LikeVO vo) {
        if (!this.isNote(vo.commentId())) return "内部类型错误,请联系管理员";
        if (hasNoteLiked(vo)){
            return "内部错误,请联系管理员";
        }else {
            supportMapper.insert(Support.initSupport(vo));

            Note note = noteService.getById(vo.noteId());
            note.increaseLikes();
            noteService.updateById(note);
            return null;
        }
    }
    @Transactional
    @Override
    public String unlikeNote(LikeVO vo) {
        if (!this.isNote(vo.commentId())) return "内部类型错误,请联系管理员";
        if (!hasNoteLiked(vo)){
            return "内部错误,请联系管理员";
        } else {
            LambdaQueryWrapper<Support> queryWrapper = new LambdaQueryWrapper<>();
            supportMapper.delete(queryWrapper
                    .eq(Support::getUserId,vo.userId())
                    .eq(Support::getNoteId,vo.noteId())
                    .isNull(Support::getCommentId));

            Note note = noteService.getById(vo.noteId());
            note.decrementLike();
            noteService.updateById(note);
            return null;
        }
    }
    @Transactional
    @Override
    public String likeComment(LikeVO vo) {
        if (!this.isComment(vo.commentId())) return "内部类型错误,请联系管理员";
        if (this.hasCommentLiked(vo)){
            return "内部错误,请联系管理员";
        }else {
            supportMapper.insert(Support.initSupport(vo));

            Comment comment = commentService.getById(vo.commentId());
            comment.increaseLikes();
            commentService.updateById(comment);
            return null;
        }
    }
    @Transactional
    @Override
    public String unlikeComment(LikeVO vo) {
        if (!this.isComment(vo.commentId())) return "内部类型错误,请联系管理员";
        if (!hasCommentLiked(vo)){
            return "内部错误,请联系管理员";
        }else {
            LambdaQueryWrapper<Support> queryWrapper = new LambdaQueryWrapper<>();
            supportMapper.delete(queryWrapper
                    .eq(Support::getUserId,vo.userId())
                    .eq(Support::getNoteId,vo.noteId())
                    .eq(Support::getCommentId,vo.commentId()));

            Comment comment = commentService.getById(vo.noteId());
            comment.decrementLike();
            commentService.updateById(comment);
            return null;
        }
    }

    private Boolean isNote(Integer commentId){
        return commentId == null;
    }
    private Boolean isComment(Integer commentId){
        return commentId != null;
    }
    private Boolean hasNoteLiked(LikeVO likeVO){
        LambdaQueryWrapper<Support> queryWrapper = new LambdaQueryWrapper<>();
        Support support = supportMapper.selectOne(queryWrapper
                .eq(Support::getUserId, likeVO.userId())
                .eq(Support::getNoteId, likeVO.noteId())
                .isNull(Support::getCommentId));
        // 返回是否点赞,是为true,否为false
        return support != null;
    }
    private Boolean hasCommentLiked(LikeVO likeVO){
        LambdaQueryWrapper<Support> queryWrapper = new LambdaQueryWrapper<>();
        Support support = supportMapper.selectOne(queryWrapper
                .eq(Support::getUserId, likeVO.userId())
                .eq(Support::getNoteId, likeVO.noteId())
                .eq(Support::getCommentId, likeVO.commentId()));
        // 返回是否点赞,是为true,否为false
        return support != null;
    }
}




