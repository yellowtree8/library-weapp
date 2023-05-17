package com.example.library.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Talk implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sendId;

    private String msg;

    private Date createTime;

    private Boolean secret;

    private String nickName;

    private String pic;

    private int unread;
}
