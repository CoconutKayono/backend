package com.coconut.backend.entity.vo.request;

import jakarta.validation.constraints.NotNull;

public record LikeVO(
        @NotNull Integer userId,
        @NotNull Integer noteId,
        Integer commentId){
}
