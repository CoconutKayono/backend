package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public static class Builder {
        private Integer id;
        private Integer userId;
        private Integer noteId;
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

        public Builder createdTime(LocalDateTime val) {
            createdTime = val;
            return this;
        }

        public LikeNote build() {
            return new LikeNote(this);
        }
    }

    private LikeNote(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.noteId = builder.noteId;
        this.createdTime = builder.createdTime;
    }

}