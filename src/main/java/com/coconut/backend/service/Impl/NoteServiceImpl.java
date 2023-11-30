package com.coconut.backend.service.Impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Account;
import com.coconut.backend.entity.dto.Note;
import com.coconut.backend.entity.vo.response.NoteVO;
import com.coconut.backend.entity.vo.response.UserVO;
import com.coconut.backend.mapper.NoteMapper;
import com.coconut.backend.service.AccountService;
import com.coconut.backend.service.NoteService;
import com.coconut.backend.utlis.FlexMarkUtils;
import com.coconut.backend.utlis.NoteUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
        implements NoteService {
    @Resource
    NoteUtils noteUtils;
    @Resource
    NoteMapper noteMapper;
    @Resource
    FlexMarkUtils flexMarkUtils;
    @Resource
    AccountService accountService;

    /**
     * 一键加载本地的笔记到数据库
     */
    public Boolean loadNotes() {
        File noteFile = new File(noteUtils.getUrl());
        File[] fileList = noteFile.listFiles();
        for (File file : Objects.requireNonNull(fileList)) {
            String path = file.getPath();
            String title = noteUtils.toTitle(path);
            LambdaQueryWrapper<Note> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Note::getTitle, title);
            if (!noteMapper.exists(queryWrapper)) {
                this.save(Note.initNote(title, noteUtils.getAuthor()));
            }
        }
        return true;
    }

    @Override
    public List<NoteVO> listNoteVOs() {
        List<Note> notes = this.list();
        return notes.stream().map(this::toNoteVO).collect(Collectors.toList());
    }

    @Override
    public NoteVO getByTitle(String title) {
        Note note = this.lambdaQuery().eq(Note::getTitle, title).one();
        return toNoteVO(note);
    }

    @Override
    public String viewNote(String title) {
        LambdaQueryWrapper<Note> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Note::getTitle, title);
        Note note = noteMapper.selectOne(queryWrapper);
        if (note == null) return "内部错误,请联系管理员";
        note.increaseView();
        noteMapper.updateById(note);
        return null;
    }

    private NoteVO toNoteVO(Note note) {
        // 封装作者视图
        if (note == null) return null;
        Integer userId = note.getUserId();
        Account account = accountService.getById(userId);
        UserVO userVO = UserVO.initUserVO(account);
        // 获取文档数据
        String title = note.getTitle();
        String path = noteUtils.toPath(title);
        File file = new File(path);
        String data = FileUtil.readString(file, StandardCharsets.UTF_8);
        String renderer = flexMarkUtils.parseMarkdown(data);
        // 生成并返回NoteVO视图对象
        return NoteVO.initNoteVO(note, userVO, renderer);
    }
}
