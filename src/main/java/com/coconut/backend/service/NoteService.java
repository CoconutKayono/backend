package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.Note;
import com.coconut.backend.entity.vo.response.NoteVO;

import java.util.List;

public interface NoteService extends IService<Note> {
    Boolean loadNotes();

    List<NoteVO> listNoteVOs();

    NoteVO getByTitle(String title);

    String viewNote(Integer noteId);
}
