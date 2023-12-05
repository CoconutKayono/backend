package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.LikeNote;
import com.coconut.backend.entity.dto.Note;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.coconut.backend.entity.vo.response.LikeVO;
import com.coconut.backend.mapper.LikeNoteMapper;
import com.coconut.backend.mapper.NoteMapper;
import com.coconut.backend.service.LikeNoteService;
import com.coconut.backend.service.NoteService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LikeNoteServiceImpl extends ServiceImpl<LikeNoteMapper, LikeNote>
        implements LikeNoteService {
    @Resource
    LikeNoteMapper likeNoteMapper;
    @Resource
    NoteMapper noteMapper;

    @Transactional
    @Override
    public LikeVO like(LikeNoteVO vo) {
        if (this.hasLiked(vo)) {
            return this.unlike(vo);
        } else {
            likeNoteMapper.insert(LikeNote.newInstance(vo));

            Note note = noteMapper.selectById(vo.noteId());
            note.increaseLikes();
            noteMapper.updateById(note);
            return new LikeVO(note.getSupport(), true);
        }
    }

    @Transactional
    @Override
    public LikeVO unlike(LikeNoteVO vo) {
        if (!this.hasLiked(vo)) {
            return this.like(vo);
        } else {
            likeNoteMapper.delete(Wrappers.<LikeNote>lambdaQuery()
                    .eq(LikeNote::getUserId, vo.userId())
                    .eq(LikeNote::getNoteId, vo.noteId())
            );

            Note note = noteMapper.selectById(vo.noteId());
            note.decrementLike();
            noteMapper.updateById(note);
            return new LikeVO(note.getSupport(), false);
        }
    }

    private Boolean hasLiked(LikeNoteVO likeNoteVO) {
        LikeNote likeNote = likeNoteMapper.selectOne(Wrappers.<LikeNote>lambdaQuery()
                .eq(LikeNote::getUserId, likeNoteVO.userId())
                .eq(LikeNote::getNoteId, likeNoteVO.noteId())
        );
        // 返回是否点赞,是为true,否为false
        return likeNote != null;
    }


}




