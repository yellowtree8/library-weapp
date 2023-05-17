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
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class YlrcSeat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer xAxis;

    private Integer yAxis;

    private Long readingRoomId;

    private Integer status;


}
