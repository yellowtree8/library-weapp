package com.example.library.mapper;

import com.example.library.entity.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-04-27
 */
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {

}
