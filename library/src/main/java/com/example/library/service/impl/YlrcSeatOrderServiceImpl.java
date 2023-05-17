package com.example.library.service.impl;

import com.example.library.entity.TimeEnum;
import com.example.library.entity.UserOrder;
import com.example.library.entity.YlrcSeatOrder;
import com.example.library.mapper.YlrcSeatOrderMapper;
import com.example.library.service.IYlrcSeatOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.library.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-03-23
 */
@Service
public class YlrcSeatOrderServiceImpl extends ServiceImpl<YlrcSeatOrderMapper, YlrcSeatOrder> implements IYlrcSeatOrderService {
    @Resource
    private YlrcSeatOrderMapper ylrcSeatOrderMapper;
    @Override
    public List<UserOrder> getorder(long student_id){return ylrcSeatOrderMapper.getorder(student_id);}

    @Override
    public List<UserOrder> getbreak(long student_id){return ylrcSeatOrderMapper.getbreak(student_id);}


    public UserOrder stuIsOrdered(Long stuId, Long currentDay) {//把往期记录过滤掉，选出正在预约中的座位

        int pm = DateUtil.isPM(); //判断当前时间是否为下午，0为上午 1为下午
        List<UserOrder> seatOrders = ylrcSeatOrderMapper.getorder(stuId);
        for (UserOrder seatOrder : seatOrders) { //判断学生是否已预约 若已预约则提示 以当先时间为基准过滤掉以前的预约

            long stuTime = DateUtil.day(seatOrder.getSubscribeTime()).getTime();
            if (stuTime > currentDay) {// 订单时间大于当前时间 就说明已经有预约了
                return seatOrder;
            }
            if (stuTime == currentDay) { //订单时间等于当前时间
                if (TimeEnum.AM.getCode() == seatOrder.getTimeCode()) {//订单时间为上午
                    if (pm == 0) {//当前时间为上午
                        return seatOrder;
                    }
                }
                if (TimeEnum.PM.getCode() == seatOrder.getTimeCode()) {//订单时间为下午
//                    if (pm == 1) {//当前时间为下午
//                        return seatOrder;
//                    }
                    return seatOrder;
                }
            }

        } //该判断已经通过之后，就说明订单表里已经都是小于当前时间的预约订单了 就是都是以前预约的记录（一个学生只能预约一次未来的时间座位）

        return null;
    }
}
