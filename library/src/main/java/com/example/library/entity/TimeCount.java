package com.example.library.entity;


import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TimeCount {
    private static final long serialVersionUID = 1L;
    private Long stuid;

    private Long month;
    private Long week;

    private Long day;

    private Long cur;

}
