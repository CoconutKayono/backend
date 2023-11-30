package com.coconut.backend.entity.vo.request;

import jakarta.validation.constraints.NotNull;

public record LikeCommentVO(@NotNull
                            Integer userId,
                            @NotNull
                            Integer noteId,
                            @NotNull
                            Integer commentId) {
}
