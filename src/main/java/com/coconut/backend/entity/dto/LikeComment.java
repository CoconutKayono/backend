package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coconut.backend.entity.vo.request.LikeCommentVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "like_comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeComment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer noteId;
    private Integer commentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    public static LikeComment newInstance(LikeCommentVO vo) {
        LikeComment likeComment = new LikeComment();
        likeComment.id = null;
        likeComment.userId = vo.userId();
        likeComment.noteId = vo.noteId();
        likeComment.commentId = vo.commentId();
        likeComment.createdTime = LocalDateTime.now();
        return likeComment;
    }
}