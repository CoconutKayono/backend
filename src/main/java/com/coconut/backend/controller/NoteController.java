package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.response.NoteVO;
import com.coconut.backend.service.NoteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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
    public RestBean<List<NoteVO>> list() {
        List<NoteVO> notes = noteService.listNoteVOs();
        if (notes != null) return RestBean.success(notes);
        else return RestBean.failure(404, "暂无任何笔记");
    }

    /**
     * 加载笔记,需要管理员权限
     *
     * @return RestBean<String>
     */
    @GetMapping("/load")
    public RestBean<String> loadNotes() {
        return noteService.loadNotes()
                ? RestBean.success("成功加载笔记")
                : RestBean.failure(520, "未知错误");
    }

}
