package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.library.entity.YlrcSeat;
import com.example.library.entity.YlrcSeatOrder;
import com.example.library.entity.YlrcStudent;
import com.example.library.entity.YlrcUser;
import com.example.library.service.IYlrcSeatOrderService;
import com.example.library.service.IYlrcSeatService;
import com.example.library.service.IYlrcStudentService;
import com.example.library.service.IYlrcUserService;
import com.example.library.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/ylrc-seat")
public class YlrcSeatController {
    @Autowired
    private IYlrcSeatService ylrcSeatService;
    @Autowired
    private IYlrcSeatOrderService ylrcSeatOrderService;

    @Autowired
    private IYlrcUserService ylrcUserService;

    @Autowired
    private IYlrcStudentService ylrcStudentService;
    @GetMapping("/getseat")
    public List<YlrcSeat> list(@RequestParam(name="room_id",required=true)Long room_id,
                               @RequestParam(name = "timecode",required = true) int timecode,
                               @RequestParam(name = "date",required = true) Long date){
        LambdaQueryWrapper<YlrcSeat> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(YlrcSeat::getReadingRoomId,room_id);
        LambdaQueryWrapper<YlrcSeatOrder> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.eq(YlrcSeatOrder::getReadingRoomId,room_id)
                .eq(YlrcSeatOrder::getTimeCode,timecode)
                .eq(YlrcSeatOrder::getSubscribeTime,DateUtil.millisecondToDate(date));
//
        List<YlrcSeatOrder> disableSeats = ylrcSeatOrderService.list(orderQueryWrapper);
        List<YlrcSeat> seats =  ylrcSeatService.list(lambdaQueryWrapper);
        if (!disableSeats.isEmpty()) {
            seats = seats.stream().map(o -> {
                YlrcSeat seat = o;
                for (YlrcSeatOrder disableSeat : disableSeats){
                    if(seat.getId().equals(disableSeat.getSeatId())){
                        seat.setStatus(2);
                    }
                }
                return seat;
        }).collect(Collectors.toList());
    }
        return seats;
}

    @GetMapping("/seatinfo")
    public YlrcUser seatinfo(@RequestParam(name="room_id",required=true)Long room_id,
                         @RequestParam(name = "xAxis",required = true) int xAxis,
                         @RequestParam(name = "yAxis",required = true) Long yAxis){
        Date currentDay = DateUtil.day(new Date());
        System.out.println(currentDay);
        int pm = DateUtil.isPM()+1; //判断当前时间是否为下午，0为上午 1为下午
        LambdaQueryWrapper<YlrcSeat> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(YlrcSeat::getReadingRoomId,room_id)
                .eq(YlrcSeat::getXAxis,xAxis)
                .eq(YlrcSeat::getYAxis,yAxis);
        YlrcSeat seat = ylrcSeatService.getOne(lambdaQueryWrapper);
        Long seatId = seat.getId();
        LambdaQueryWrapper<YlrcSeatOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(YlrcSeatOrder::getSeatId,seatId)
                .apply("date_format(subscribe_time,'%Y-%m-%d') = {0}",currentDay)
                .eq(YlrcSeatOrder::getTimeCode,pm);
        YlrcSeatOrder seatOrder = ylrcSeatOrderService.getOne(orderWrapper);
        if(Objects.nonNull(seatOrder)){

            Long stu_id = seatOrder.getStudentId();
            LambdaQueryWrapper<YlrcStudent> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.eq(YlrcStudent::getId,stu_id);

            YlrcStudent student = ylrcStudentService.getOne(userWrapper);
            Long user_id = student.getUserId();
            YlrcUser user = ylrcUserService.getById(user_id);
            user.setStudentId(stu_id);
            if(seatOrder.getState()==1){
                user.setStatus(1);
            }else if(seatOrder.getState()==0){
                user.setStatus(0);
            }else if(seatOrder.getState()==2){
                user.setStatus(2);
            }
            return user;
        }else{
            return null;
        }
    }

}