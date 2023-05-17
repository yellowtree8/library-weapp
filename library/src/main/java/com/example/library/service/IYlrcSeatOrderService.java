package com.example.library.service;

import com.example.library.entity.UserOrder;
import com.example.library.entity.YlrcSeatOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-03-23
 */
public interface IYlrcSeatOrderService extends IService<YlrcSeatOrder> {

    List<UserOrder> getorder(long student_id);


    List<UserOrder> getbreak(long student_id);

    UserOrder stuIsOrdered(Long stuId, Long currentDay);
}
