package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coconut.backend.entity.vo.request.LikeVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "support")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Support implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer noteId;
    private Integer commentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static Support initSupport(LikeVO likeVO) {
        Support support = new Support();
        support.id = null;
        support.userId = likeVO.userId();
        support.noteId = likeVO.noteId();
        support.commentId = likeVO.commentId();
        support.createdAt = LocalDateTime.now();
        return support;
    }
}