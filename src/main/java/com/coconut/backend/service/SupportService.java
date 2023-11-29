package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.Support;
import com.coconut.backend.entity.vo.request.LikeVO;
import org.springframework.transaction.annotation.Transactional;


public interface SupportService extends IService<Support> {
    String likeNote(LikeVO vo);
    String unlikeNote(LikeVO vo);
    String likeComment(LikeVO vo);
    String unlikeComment(LikeVO vo);
}
