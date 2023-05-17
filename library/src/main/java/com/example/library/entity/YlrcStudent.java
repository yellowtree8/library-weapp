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
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YlrcStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime studentBirthday;

    private Integer studentCredits;

    private String studentSno;

    private Long clbumId;

    private Long userId;

    private Long clazzId;


}
