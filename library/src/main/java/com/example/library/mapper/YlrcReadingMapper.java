package com.example.library.mapper;

import com.example.library.entity.Reading;
import com.example.library.entity.YlrcReading;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-03-19
 */
@Mapper
public interface YlrcReadingMapper extends BaseMapper<YlrcReading> {
    List<Reading> getRoom(Integer readingtype_id);
}
