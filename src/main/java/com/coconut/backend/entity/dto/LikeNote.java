package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;

import javax.naming.spi.ObjectFactory;
import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "like_note")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeNote implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer noteId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    public static LikeNote createLikeNote(LikeNoteVO likeNoteVO) {
        LikeNote likeNote = new LikeNote();
        likeNote.id = null;
        likeNote.userId = likeNoteVO.userId();
        likeNote.noteId = likeNoteVO.noteId();
        likeNote.createdTime = LocalDateTime.now();
        return likeNote;
    }
}