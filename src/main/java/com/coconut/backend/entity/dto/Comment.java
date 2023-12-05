package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer noteId;
    private Integer parentId;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    private Integer support;

    public void increaseLikes() {
        this.setSupport(getSupport() + 1);
    }

    public void decrementLike() {
        this.setSupport(getSupport() - 1);
    }

    public static class Builder {
        private Integer id;
        private Integer userId;
        private Integer noteId;
        private Integer parentId;
        private String comment;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdTime;
        private Integer support;

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

        public Builder parentId(Integer val) {
            parentId = val;
            return this;
        }

        public Builder comment(String val) {
            comment = val;
            return this;
        }

        public Builder createdTime(LocalDateTime val) {
            createdTime = val;
            return this;
        }

        public Builder support(Integer val) {
            support = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    private Comment(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.noteId = builder.noteId;
        this.parentId = builder.parentId;
        this.comment = builder.comment;
        this.createdTime = builder.createdTime;
        this.support = builder.support;
    }
}