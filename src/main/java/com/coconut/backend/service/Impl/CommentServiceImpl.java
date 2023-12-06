package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Account;
import com.coconut.backend.entity.dto.Comment;
import com.coconut.backend.entity.dto.LikeComment;
import com.coconut.backend.entity.dto.LikeNote;
import com.coconut.backend.entity.vo.request.LikeCommentVO;
import com.coconut.backend.entity.vo.request.LikeNoteVO;
import com.coconut.backend.entity.vo.response.CommentVO;
import com.coconut.backend.entity.vo.response.UserVO;
import com.coconut.backend.mapper.AccountMapper;
import com.coconut.backend.mapper.CommentMapper;
import com.coconut.backend.mapper.LikeCommentMapper;
import com.coconut.backend.service.CommentService;
import com.coconut.backend.service.LikeCommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Resource
    CommentMapper commentMapper;
    @Resource
    AccountMapper accountMapper;
    @Resource
    LikeCommentMapper likeCommentMapper;
    @Override
    public List<CommentVO> listCommentVOs() {
        List<Comment> comments = commentMapper.selectList(Wrappers.emptyWrapper());
        return comments.stream().map(this::toCommentVO).collect(Collectors.toList());
    }

    @Override
    public List<CommentVO> listCommentVOs(Integer id) {
        List<Comment> comments = commentMapper.selectList(Wrappers.emptyWrapper());
        return comments.stream().map(comment -> this.toCommentVO(comment,id)).collect(Collectors.toList());
    }

    private CommentVO toCommentVO(Comment comment){
        Integer userId = comment.getUserId();
        Account account = accountMapper.selectById(userId);
        UserVO userVO = UserVO.newInstance(account);
        return CommentVO.newInstance(comment, userVO, false);
    }

    private CommentVO toCommentVO(Comment comment,Integer id){
        Integer userId = comment.getUserId();
        Account account = accountMapper.selectById(userId);
        UserVO userVO = UserVO.newInstance(account);

        LikeCommentVO likeCommentVO = new LikeCommentVO(id,comment.getNoteId(),comment.getId());
        Boolean hasLiked = this.hasLiked(likeCommentVO);
        return CommentVO.newInstance(comment, userVO, hasLiked);
    }
    private Boolean hasLiked(LikeCommentVO likeCommentVO) {
        LikeComment likeComment = likeCommentMapper.selectOne(Wrappers.<LikeComment>lambdaQuery()
                .eq(LikeComment::getId,likeCommentVO.userId())
                .eq(LikeComment::getUserId, likeCommentVO.noteId())
                .eq(LikeComment::getNoteId, likeCommentVO.commentId())
        );
        // 返回是否点赞,是为true,否为false
        return likeComment != null;
    }
}




