package com.example.library.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YlrcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String email;

    private String headPic;

    private String mobile;

    private String password;

    private Integer sex;

    private Integer status;

    private String username;

    private Long roleId;

    private String nickName;

    private Long studentId;

    private Long teacherId;


}
