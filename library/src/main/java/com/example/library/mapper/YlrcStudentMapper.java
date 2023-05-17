package com.example.library.mapper;

import com.example.library.entity.YlrcReading;
import com.example.library.entity.YlrcStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-03-23
 */
@Mapper
public interface YlrcStudentMapper extends BaseMapper<YlrcStudent> {
    List<YlrcStudent> stuid(Integer user_id);
}
