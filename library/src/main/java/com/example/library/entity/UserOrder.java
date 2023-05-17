package com.example.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserOrder {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer orderId;

    private Long studentId;

    private Date createTime;

    private Date updateTime;

    private Date subscribeTime;

    private Integer timeCode;

    private String name;

    private Long seatId;

    private Integer status;

    private Integer xAxis;

    private Integer yAxis;

    private Integer state;

}
