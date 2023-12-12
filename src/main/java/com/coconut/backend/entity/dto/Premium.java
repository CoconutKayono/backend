package com.coconut.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("premium")
public class Premium implements Serializable {
    private Integer userId;
    private String vipType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTime;

    private Premium(Builder builder) {
        this.userId = builder.userId;
        this.vipType = builder.vipType;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.lastTime = builder.lastTime;
    }

    public static class Builder {
        private Integer userId;
        private String vipType;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime lastTime;

        public Builder userId(Integer val) {
            userId = val;
            return this;
        }

        public Builder vipType(String val) {
            vipType = val;
            return this;
        }

        public Builder startTime(LocalDateTime val) {
            startTime = val;
            return this;
        }

        public Builder endTime(LocalDateTime val) {
            endTime = val;
            return this;
        }

        public Builder lastTime(LocalDateTime val) {
            lastTime = val;
            return this;
        }

        public Premium build() {
            return new Premium(this);
        }
    }
}