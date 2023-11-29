package com.coconut.backend.entity.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record EmailVerifyCodeVO(@Email
                                @Length(min = 4) String email,
                                @Pattern(regexp = "register|reset") String type) {
}
