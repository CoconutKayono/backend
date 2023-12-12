package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.Note;
import com.coconut.backend.entity.vo.request.UploadNoteVO;
import com.coconut.backend.entity.vo.response.NoteVO;

import java.io.IOException;
import java.util.List;

public interface NoteService extends IService<Note> {
    Boolean loadNotes();

    List<NoteVO> listNoteVOs();

    List<NoteVO> listNoteVOs(Integer userId);

    NoteVO getByTitle(String title);

    String viewNote(String title);

    String saveNote(Integer userId, UploadNoteVO uploadNoteVO) throws IOException;
}
