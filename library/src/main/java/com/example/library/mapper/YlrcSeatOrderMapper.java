package com.example.library.mapper;

import com.example.library.entity.UserOrder;
import com.example.library.entity.YlrcSeatOrder;
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
public interface YlrcSeatOrderMapper extends BaseMapper<YlrcSeatOrder> {
    List<UserOrder> getorder(long student_id);
    List<UserOrder> getbreak(long student_id);
}
