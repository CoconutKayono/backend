package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("note")
public class Note {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;
    private Integer view;
    private Integer support;

    public void increaseLikes(){
        this.setSupport(getSupport()+1);
    }
    public void decrementLike(){
        this.setSupport(getSupport()-1);
    }
    public void increaseView(){
        this.setView(getView()+1);
    }
    public static Note initNote(String title, Integer userId) {
        Note note = new Note();
        note.setTitle(title);
        note.setUserId(userId);
        note.setUploadTime(LocalDateTime.now());
        note.setView(0);
        note.setSupport(0);
        return note;
    }
}
