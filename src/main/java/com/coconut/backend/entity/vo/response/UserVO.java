package com.coconut.backend.entity.vo.response;

import com.coconut.backend.entity.dto.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * 与数据传输对象相比:没有邮箱和密码的视图
 */
@Data
public class UserVO {
    private Integer id;
    private String username;
    private String avatar;
    private Integer experience;
    private String role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

    public static UserVO newInstance(Account account) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(account, userVO);
        return userVO;
    }
}
