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
    private UserVO userVO;
    private String title;
    private String data;
    private String previewImageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    private Integer view;
    private Integer support;
    private Boolean isLiked;

    public static NoteVO newInstance(Note note, UserVO userVO) {
        NoteVO noteVO = new NoteVO(userVO);
        BeanUtils.copyProperties(note, noteVO);
        return noteVO;
    }
}
