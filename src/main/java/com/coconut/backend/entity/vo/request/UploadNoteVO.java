package com.coconut.backend.entity.vo.request;

import org.springframework.web.multipart.MultipartFile;

public record UploadNoteVO(String publicRange,
                           boolean canBeCommented,
                           MultipartFile multipartFile) {
}
