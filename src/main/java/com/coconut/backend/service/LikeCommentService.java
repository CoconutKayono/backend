package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.LikeComment;
import com.coconut.backend.entity.vo.request.LikeCommentVO;
import com.coconut.backend.entity.vo.response.LikeVO;

public interface LikeCommentService extends IService<LikeComment> {
    LikeVO like(LikeCommentVO vo);

    LikeVO unlike(LikeCommentVO vo);
}
