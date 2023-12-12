package com.coconut.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coconut.backend.entity.dto.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}




