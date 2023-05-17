package com.example.library.service;

import com.example.library.entity.CardNum;
import com.example.library.entity.CardOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.library.entity.UsedCard;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-04-01
 */
public interface ICardOrderService extends IService<CardOrder> {
    List<CardNum> cardnum(Integer status, Long student_id);


    List<UsedCard> usedcard(Long student_id);
}
