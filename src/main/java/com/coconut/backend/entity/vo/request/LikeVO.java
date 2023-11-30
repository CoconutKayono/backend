package com.coconut.backend.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record LikeVO(
        @NotNull
        Integer userId,
        @NotNull
        Integer noteId,
        @Null
        Integer commentId) {
}
