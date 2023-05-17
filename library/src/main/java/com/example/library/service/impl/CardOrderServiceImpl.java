package com.example.library.service.impl;

import com.example.library.entity.CardNum;
import com.example.library.entity.CardOrder;
import com.example.library.entity.UsedCard;
import com.example.library.mapper.CardOrderMapper;
import com.example.library.service.ICardOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-04-01
 */
@Service
public class CardOrderServiceImpl extends ServiceImpl<CardOrderMapper, CardOrder> implements ICardOrderService {
    @Resource
    private CardOrderMapper cardOrderMapper;
    @Override
    public List<CardNum> cardnum(Integer status, Long student_id){return cardOrderMapper.cardnum(status,student_id);
    }
    @Override
    public List<UsedCard> usedcard(Long student_id) {
        return cardOrderMapper.usedcard(student_id);
    }
}
