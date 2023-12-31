package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("note")
public class Note implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String title;
    private String catalogue;
    private String data;
    private String previewImageUrl;
    private String publicRange;
    private Boolean canBeCommented;
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
}
