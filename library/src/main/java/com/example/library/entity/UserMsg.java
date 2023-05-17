package com.example.library.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long sendId;

    private String msg;

    private Long acceptId;

    private Date createTime;

    private Boolean secret;

    @TableField(value = "`read`")
    private int read;
}
