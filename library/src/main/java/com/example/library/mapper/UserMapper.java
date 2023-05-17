package com.example.library.mapper;

import com.example.library.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-03-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
