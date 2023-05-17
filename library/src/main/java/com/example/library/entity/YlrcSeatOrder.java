package com.example.library.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class YlrcSeatOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createTime;

    private Date updateTime;

    private Date subscribeTime;

    private Integer timeCode;

    private Long readingRoomId;

    private Long seatId;

    private Long studentId;

    private Integer state;

    private Date leaveTime;

    private Long duration;
}
