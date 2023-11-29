package com.coconut.backend.entity.vo.response;

import java.util.Date;

public record AuthorizeVO(String username,
                          String role,
                          String token,
                          Date expire) {

}
