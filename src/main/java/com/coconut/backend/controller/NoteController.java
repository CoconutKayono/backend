package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.response.NoteVO;
import com.coconut.backend.service.NoteService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    @Resource
    NoteService noteService;

    @GetMapping("/{title}")
    public RestBean<NoteVO> getByTitle(@PathVariable String title) {
        NoteVO noteVO = noteService.getByTitle(title);
        return noteVO == null ? RestBean.failure(404, "未查找到该资源") : RestBean.success(noteVO);
    }

    @GetMapping("/list")
    public RestBean<List<NoteVO>> list(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        List<NoteVO> notes;
        if (id == null) {
            notes = noteService.listNoteVOs();
        } else {
            notes = noteService.listNoteVOs(id);
        }
        if (notes != null) return RestBean.success(notes);
        else return RestBean.failure(404, "暂无任何笔记");

    }

    @GetMapping("/view/{title}")
    public RestBean<Void> viewNote(@PathVariable String title) {
        String message = noteService.viewNote(title);
        return message == null ? RestBean.success() : RestBean.failure(520, message);
    }

    /**
     * 加载笔记,需要管理员权限
     *
     * @return RestBean<String>
     */
    @GetMapping("/load")
    public RestBean<Void> loadNotes() {
        return noteService.loadNotes()
                ? RestBean.success()
                : RestBean.failure(400, "未知错误");
    }

}
