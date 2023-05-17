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
 * @since 2023-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long sendId;

    private Long acceptId;

    private Boolean status;


}
