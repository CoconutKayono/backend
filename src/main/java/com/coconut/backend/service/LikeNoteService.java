package com.coconut.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coconut.backend.entity.dto.LikeNote;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.coconut.backend.entity.vo.response.LikeVO;


public interface LikeNoteService extends IService<LikeNote> {
    LikeVO like(LikeNoteVO vo);

    LikeVO unlike(LikeNoteVO vo);

}
