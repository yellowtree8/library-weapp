package com.example.library.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

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
public class OrderBody implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long subscribeTime;

    private Integer timeCode;

    private Long readingRoomId;

    private Long seatId;

    private Long studentId;


}
