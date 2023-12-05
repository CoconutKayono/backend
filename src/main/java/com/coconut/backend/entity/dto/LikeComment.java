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

    public static class Builder {
        private Integer id;
        private Integer userId;
        private Integer noteId;
        private Integer commentId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdTime;

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder userId(Integer val) {
            userId = val;
            return this;
        }

        public Builder noteId(Integer val) {
            noteId = val;
            return this;
        }

        public Builder commentId(Integer val) {
            commentId = val;
            return this;
        }

        public Builder createdTime(LocalDateTime val) {
            createdTime = val;
            return this;
        }

        public LikeComment build() {
            return new LikeComment(this);
        }
    }

    private LikeComment(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.noteId = builder.noteId;
        this.commentId = builder.commentId;
        this.createdTime = builder.createdTime;
    }

}