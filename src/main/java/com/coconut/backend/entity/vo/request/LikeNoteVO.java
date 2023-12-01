package com.coconut.backend.entity.vo.request;

import jakarta.validation.constraints.NotNull;

public record LikeNoteVO(@NotNull Integer userId,
                         @NotNull Integer noteId) {
}
