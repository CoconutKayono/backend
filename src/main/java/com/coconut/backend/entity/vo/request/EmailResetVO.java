package com.coconut.backend.entity.vo.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record EmailResetVO(@Email
                           @Length(min = 4) String email,
                           @Length(min = 6, max = 6) String code,
                           @Length(min = 6, max = 20) String password
) {
}
