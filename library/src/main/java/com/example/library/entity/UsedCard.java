package com.example.library.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsedCard implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private Long toolId;

    private String name;

    private Integer cost;

    private String disc;



    private Date createTime;

    private Date updateTime;

    private Long studentId;

    private Integer status;


}