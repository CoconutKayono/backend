package com.coconut.backend.entity.vo.response;

import com.coconut.backend.entity.dto.Note;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * 与数据传输对象相比:存储了用户视图的视图
 */
@Data
public class NoteVO {
    public NoteVO(UserVO userVO) {
        this.userVO = userVO;
    }

    private Integer id;
    private String title;
    private UserVO userVO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;
    private Integer view;
    private Integer support;
    private String data;

    public static NoteVO initNoteVO(Note note, UserVO userVO, String data) {
        NoteVO noteVO = new NoteVO(userVO);
        BeanUtils.copyProperties(note, noteVO);
        noteVO.setData(data);
        return noteVO;
    }
}
