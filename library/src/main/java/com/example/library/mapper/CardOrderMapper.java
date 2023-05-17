package com.example.library.mapper;

import com.example.library.entity.CardNum;
import com.example.library.entity.CardOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.library.entity.UsedCard;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-04-01
 */
@Mapper
public interface CardOrderMapper extends BaseMapper<CardOrder> {
    List<CardNum> cardnum(Integer status, Long student_id);

    List<UsedCard> usedcard(Long student_id);
}
