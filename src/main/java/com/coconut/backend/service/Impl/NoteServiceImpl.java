package com.coconut.backend.service.Impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Account;
import com.coconut.backend.entity.dto.LikeNote;
import com.coconut.backend.entity.dto.Note;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.coconut.backend.entity.vo.request.UploadNoteVO;
import com.coconut.backend.entity.vo.response.NoteVO;
import com.coconut.backend.entity.vo.response.UserVO;
import com.coconut.backend.mapper.AccountMapper;
import com.coconut.backend.mapper.LikeNoteMapper;
import com.coconut.backend.mapper.NoteMapper;
import com.coconut.backend.service.NoteService;
import com.coconut.backend.utlis.FlexMarkUtils;
import com.coconut.backend.utlis.JsoupUtils;
import com.coconut.backend.utlis.NoteUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
    LikeNoteMapper likeNoteMapper;
    @Resource
    FlexMarkUtils flexMarkUtils;
    @Resource
    JsoupUtils jsoupUtils;
    @Resource
    AccountMapper accountMapper;

    /**
     * 一键加载本地的笔记到数据库
     */
    public Boolean loadNotes() {
        File noteFile = new File(noteUtils.getUrl());
        File[] fileList = noteFile.listFiles();
        for (File file : Objects.requireNonNull(fileList)) {
            String title = noteUtils.toTitle(file.getName());

            if (!noteMapper.exists(Wrappers.<Note>lambdaQuery()
                    .eq(Note::getTitle, title))) {
                String markdownContent = FileUtil.readString(file, StandardCharsets.UTF_8);
                String html = this.parseMarkdown(markdownContent);
                String catalogue = jsoupUtils.getCatalogue(html);
                String data = jsoupUtils.getData(html);
                String previewImageUrl = jsoupUtils.getFirstImageForPreview(html);

                noteMapper.insert(Note.builder()
                        .id(null)
                        .userId(noteUtils.getAuthorId())
                        .title(title)
                        .catalogue(catalogue)
                        .data(data)
                        .previewImageUrl(previewImageUrl)
                        .publicRange("public")
                        .canBeCommented(true)
                        .createdTime(LocalDateTime.now())
                        .view(0)
                        .support(0)
                        .build());
            }
        }
        return true;
    }

    @Override
    public List<NoteVO> listNoteVOs() {
        List<Note> notes = noteMapper.selectList(Wrappers.emptyWrapper());
        return notes.stream().map(this::toNoteVO).collect(Collectors.toList());
    }

    @Override
    public List<NoteVO> listNoteVOs(Integer id) {
        List<Note> notes = noteMapper.selectList(Wrappers.emptyWrapper());
        return notes.stream().map((note -> this.toNoteVO(note, id))).collect(Collectors.toList());
    }

    @Override
    public NoteVO getByTitle(String title) {
        Note note = this.lambdaQuery().eq(Note::getTitle, title).one();
        return toNoteVO(note);
    }

    /**
     * 浏览笔记
     */
    @Override
    public String viewNote(String title) {
        Note note = noteMapper.selectOne(Wrappers.<Note>lambdaQuery()
                .eq(Note::getTitle, title)
        );
        if (note == null) return "内部错误,请联系管理员";
        note.increaseView();
        noteMapper.updateById(note);
        return null;
    }

    @Override
    public String saveNote(Integer userId, UploadNoteVO uploadNoteVO) throws IOException {
        MultipartFile multipartFile = uploadNoteVO.multipartFile();

        String filename = multipartFile.getOriginalFilename();
        String title = noteUtils.toTitle(Objects.requireNonNull(filename));

        byte[] fileBytes = multipartFile.getBytes();
        String markdown = new String(fileBytes, StandardCharsets.UTF_8);

        String html = this.parseMarkdown(markdown);
        String catalogue = jsoupUtils.getCatalogue(html);
        String data = jsoupUtils.getData(html);
        String previewImageUrl = jsoupUtils.getFirstImageForPreview(html);

        Note note = Note.builder()
                .id(null)
                .userId(userId)
                .title(title)
                .catalogue(catalogue)
                .data(data)
                .previewImageUrl(previewImageUrl)
                .publicRange(uploadNoteVO.publicRange())
                .canBeCommented(uploadNoteVO.canBeCommented())
                .createdTime(LocalDateTime.now())
                .view(0)
                .support(0)
                .build();
        int insert = noteMapper.insert(note);
        return insert > 0 ? null : "内部错误,请联系管理员";
    }

    private NoteVO toNoteVO(Note note) {
        // 封装作者视图
        if (note == null) return null;
        Integer userId = note.getUserId();
        Account account = accountMapper.selectById(userId);
        UserVO userVO = UserVO.newInstance(account);
        // 生成并返回NoteVO视图对象
        return NoteVO.newInstance(note, userVO, false);
    }

    private NoteVO toNoteVO(Note note, Integer id) {
        // 封装作者视图
        if (note == null) return null;
        Integer userId = note.getUserId();
        Account account = accountMapper.selectById(userId);
        UserVO userVO = UserVO.newInstance(account);
        // 检测用户是否为该文章点过赞
        LikeNoteVO likeNoteVO = new LikeNoteVO(id, note.getId());
        Boolean hasLiked = this.hasLiked(likeNoteVO);
        // 生成并返回NoteVO视图对象
        return NoteVO.newInstance(note, userVO, hasLiked);
    }

    private String parseMarkdown(String markdownContent) {
        String parsedHtml = flexMarkUtils.parseMarkdown(markdownContent);
        return jsoupUtils.getModifyHtml(parsedHtml);
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
