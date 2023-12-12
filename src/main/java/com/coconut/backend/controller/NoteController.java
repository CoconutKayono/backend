package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.UploadNoteVO;
import com.coconut.backend.entity.vo.response.NoteVO;
import com.coconut.backend.service.NoteService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/note")
public class NoteController {
    @Resource
    NoteService noteService;

    @GetMapping("/{title}")
    public RestBean<NoteVO> queryNoteByTitle(@PathVariable String title) {
        NoteVO noteVO = noteService.getByTitle(title);
        return noteVO == null ? RestBean.failure(404, "未查找到该资源") : RestBean.success(noteVO);
    }

    @GetMapping("/list")
    public RestBean<List<NoteVO>> queryNotes(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        List<NoteVO> notes;
        if (id == null) {
            notes = noteService.listNoteVOs();
        } else {
            notes = noteService.listNoteVOs(id);
        }
        if (notes != null) return RestBean.success(notes);
        else return RestBean.failure(404, "暂无任何笔记");

    }

    @PostMapping("/postNote")
    public RestBean<String> addNote(HttpServletRequest request, UploadNoteVO uploadNoteVO) throws IOException {
        Integer userId = (Integer) request.getAttribute("userId");
        String message = noteService.saveNote(userId, uploadNoteVO);
        return message == null ? RestBean.success() : RestBean.failure(520,message);
    }

    @DeleteMapping("/{title}")
    public RestBean<String> deleteNote(@PathVariable String title) {
        NoteVO noteVO = noteService.getByTitle(title);
        return noteVO == null ? RestBean.failure(404, "未查找到该资源") : RestBean.success();
    }

    @GetMapping("/view/{title}")
    public RestBean<Void> viewNote(@PathVariable String title) {
        String message = noteService.viewNote(title);
        return message == null ? RestBean.success() : RestBean.failure(520, message);
    }

    @GetMapping("/load")
    public RestBean<Void> loadNotes() {
        return noteService.loadNotes()
                ? RestBean.success()
                : RestBean.failure(400, "未知错误");
    }

}
