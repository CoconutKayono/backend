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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer senderId;
    private Integer recipientId;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Message(Builder builder) {
        this.id = builder.id;
        this.senderId = builder.senderId;
        this.recipientId = builder.recipientId;
        this.message = builder.message;
        this.createTime = builder.createTime;
    }

    public static class Builder {
        private Integer id;
        private Integer senderId;
        private Integer recipientId;
        private String message;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder senderId(Integer val) {
            senderId = val;
            return this;
        }

        public Builder recipientId(Integer val) {
            recipientId = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder createTime(LocalDateTime val) {
            createTime = val;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}