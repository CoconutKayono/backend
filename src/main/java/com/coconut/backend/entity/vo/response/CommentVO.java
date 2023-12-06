package com.coconut.backend.entity.vo.response;

import com.coconut.backend.entity.dto.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class CommentVO {
    private Integer id;
    private UserVO userVO;
    private Integer noteId;
    private Integer parentId;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    private Integer support;
    private Boolean isLiked;

    public static CommentVO newInstance(Comment comment, UserVO userVO, Boolean isLiked) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        commentVO.setUserVO(userVO);
        commentVO.setIsLiked(isLiked);
        return commentVO;
    }
}
