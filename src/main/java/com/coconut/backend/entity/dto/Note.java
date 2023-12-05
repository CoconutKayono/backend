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
    private Integer userId;
    private String title;
    private String data;
    private String previewImageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    private Integer view;
    private Integer support;

    public void increaseLikes() {
        this.setSupport(getSupport() + 1);
    }

    public void decrementLike() {
        this.setSupport(getSupport() - 1);
    }

    public void increaseView() {
        this.setView(getView() + 1);
    }

    public static Note newInstance(Integer userId, String title, String data, String previewImageUrl) {
        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(title);
        note.setData(data);
        note.setPreviewImageUrl(previewImageUrl);
        note.setCreatedTime(LocalDateTime.now());
        note.setView(0);
        note.setSupport(0);
        return note;
    }
}
