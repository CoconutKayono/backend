package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.Support;
import com.coconut.backend.entity.vo.request.LikeVO;


public interface SupportService extends IService<Support> {
    Boolean support(LikeVO likeVO);
}
