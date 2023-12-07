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
    private String catalogue;
    private String data;
    private String previewImageUrl;
    private String publicRange;
    private Boolean canBeCommented;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    private Integer view;
    private Integer support;

    private Note(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.title = builder.title;
        this.catalogue = builder.catalogue;
        this.data = builder.data;
        this.previewImageUrl = builder.previewImageUrl;
        this.publicRange = builder.publicRange;
        this.canBeCommented = builder.canBeCommented;
        this.createdTime = builder.createdTime;
        this.view = builder.view;
        this.support = builder.support;
    }

    public void increaseLikes() {
        this.setSupport(getSupport() + 1);
    }

    public void decrementLike() {
        this.setSupport(getSupport() - 1);
    }

    public void increaseView() {
        this.setView(getView() + 1);
    }

    public static class Builder {
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

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder userId(Integer val) {
            userId = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder catalogue(String val) {
            catalogue = val;
            return this;
        }

        public Builder data(String val) {
            data = val;
            return this;
        }

        public Builder previewImageUrl(String val) {
            previewImageUrl = val;
            return this;
        }

        public Builder publicRange(String val) {
            publicRange = val;
            return this;
        }

        public Builder canBeCommented(Boolean val) {
            canBeCommented = val;
            return this;
        }

        public Builder createdTime(LocalDateTime val) {
            createdTime = val;
            return this;
        }

        public Builder view(Integer val) {
            view = val;
            return this;
        }

        public Builder support(Integer val) {
            support = val;
            return this;
        }

        public Note build() {
            return new Note(this);
        }
    }
}
