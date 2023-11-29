package com.coconut.backend.service.Impl;

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


@Service
public class SupportServiceImpl extends ServiceImpl<SupportMapper, Support>
    implements SupportService{
    @Resource
    SupportMapper supportMapper;
    @Resource
    CommentService commentService;
    @Resource
    NoteService noteService;
    @Override
    public Boolean support(LikeVO likeVO) {
        Integer id = likeVO.id();
        String type = likeVO.type();
        if ("Comment".equals(type)){
            Comment comment = commentService.getById(id);
            comment.setSupport(comment.getSupport()+1);
            commentService.updateById(comment);
        }else if ("Note".equals(type)){
            Note note = noteService.getById(id);
            note.setSupport(note.getSupport()+1);
            noteService.updateById(note);
        }else {
            return null;
        }
        return null;
    }
}




