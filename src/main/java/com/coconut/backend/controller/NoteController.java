package com.coconut.backend.controller;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.vo.request.UploadNoteVO;
import com.coconut.backend.entity.vo.response.NoteVO;
import com.coconut.backend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Note参数")
public class NoteController {
    @Resource
    NoteService noteService;

    @Operation(summary = "无:通过标题查询博客")
    @Parameters({
            @Parameter(name = "title",description = "标题",in = ParameterIn.PATH),
    })
    @GetMapping("/{title}")
    public RestBean<NoteVO> queryNoteByTitle(@PathVariable String title) {
        NoteVO noteVO = noteService.getByTitle(title);
        return noteVO == null ? RestBean.failure(404, "未查找到该资源") : RestBean.success(noteVO);
    }

    @Operation(summary = "无:获取所有博客")
    @GetMapping("/guest/list")
    public RestBean<List<NoteVO>> queryNotes() {
        List<NoteVO> notes = noteService.listNoteVOs();
        return notes != null ? RestBean.success(notes) : RestBean.failure(404, "暂无任何评论");
    }

    @Operation(summary = "用户:获取所有博客")
    @Parameters({
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @GetMapping("/loggedIn/list")
    public RestBean<List<NoteVO>> loggedInQueryNotes(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        List<NoteVO> notes = noteService.listNoteVOs(userId);
        return notes != null ? RestBean.success(notes) : RestBean.failure(404, "暂无任何评论");
    }

    @Operation(summary = "用户:上传博客")
    @Parameters({
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @PostMapping("/loggedIn/postNote")
    public RestBean<String> addNote(HttpServletRequest request, UploadNoteVO uploadNoteVO) throws IOException {
        Integer userId = (Integer) request.getAttribute("userId");
        String message = noteService.saveNote(userId, uploadNoteVO);
        return message == null ? RestBean.success() : RestBean.failure(520,message);
    }

    @Operation(summary = "用户:删除博客")
    @Parameters({
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @DeleteMapping("/loggedIn/{title}")
    public RestBean<String> deleteNote(@PathVariable String title) {
        NoteVO noteVO = noteService.getByTitle(title);
        return noteVO == null ? RestBean.failure(404, "未查找到该资源") : RestBean.success();
    }

    /*需要删改*/
    @GetMapping("/view/{title}")
    public RestBean<Void> viewNote(@PathVariable String title) {
        String message = noteService.viewNote(title);
        return message == null ? RestBean.success() : RestBean.failure(520, message);
    }

    @Operation(summary = "管理员:本地文件一键上传")
    @GetMapping("/manager/load")
    public RestBean<Void> loadNotes() {
        return noteService.loadNotes()
                ? RestBean.success()
                : RestBean.failure(400, "未知错误");
    }

}
