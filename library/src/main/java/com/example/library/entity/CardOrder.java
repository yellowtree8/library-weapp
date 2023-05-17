package com.example.library.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CardOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long toolId;

    private Date createTime;

    private Date updateTime;

    private Long studentId;

    private Integer status;


}
