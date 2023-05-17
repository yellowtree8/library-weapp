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
 * @since 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YlrcClazz implements Serializable {

    private Long id;

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String college;

    private String major;

    private Long teacherId;

    private String claname;


}
