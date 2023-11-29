package com.coconut.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coconut.backend.entity.dto.Note;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper extends BaseMapper<Note> {
}
