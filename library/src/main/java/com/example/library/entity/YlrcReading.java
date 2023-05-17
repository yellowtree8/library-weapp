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
public class YlrcReading implements Serializable {

    private int id;

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer lie;


    private String name;

    @TableField(value = "`row`")
    private Integer row;

    @TableField(value = "`readingtype_id`")
    private Long readingtypeId;




}
