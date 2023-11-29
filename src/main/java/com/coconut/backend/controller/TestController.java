package com.coconut.backend.controller;

import cn.hutool.core.io.FileUtil;
import com.coconut.backend.service.AccountService;
import com.coconut.backend.service.NoteService;
import com.coconut.backend.utlis.NoteUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Resource
    NoteUtils noteUtils;
    @Resource
    AccountService accountService;
    @Resource
    NoteService noteService;
    @GetMapping("/hello")
    public String test() {
        File file = new File("D:\\Coconut\\note\\notes\\Redis数据库.md");
        String readString = FileUtil.readString(file, StandardCharsets.UTF_8);
        return readString;
    }
}
