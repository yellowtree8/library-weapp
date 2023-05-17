package com.example.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.sql.Blob;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tzgg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "art_id", type = IdType.AUTO)
    private Integer artId;

    private String title;

    private String  photo;

    private String article;

    private String author;

    private LocalDateTime createTime;


}
