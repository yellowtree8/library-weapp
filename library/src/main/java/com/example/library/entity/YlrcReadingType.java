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
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YlrcReadingType implements Serializable {

    private static final long serialVersionUID = 1L;


    private long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer integral;

    private String name;


}
