package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.library.entity.Reading;
import com.example.library.entity.User;
import com.example.library.entity.YlrcReading;
import com.example.library.entity.YlrcSeatOrder;
import com.example.library.service.IYlrcReadingService;
import com.example.library.service.IYlrcSeatOrderService;
import com.example.library.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/ylrc-reading")
public class YlrcReadingController {
    @Autowired
    private IYlrcReadingService ylrcReadingService;
    @Autowired
    private IYlrcSeatOrderService ylrcSeatOrderService;
    @GetMapping("/list")
    public List<Reading> list(@RequestParam(name = "readingtype_id",required = true)Integer readingtype_id,
                              @RequestParam(name = "subscribe_time",required = true)Long subscribe_time,
                              @RequestParam(name = "timecode",required = true)int timecode){
        LambdaQueryWrapper<YlrcSeatOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YlrcSeatOrder::getSubscribeTime, DateUtil.millisecondToDate(subscribe_time))
                .eq(YlrcSeatOrder::getTimeCode,timecode);
        List<YlrcSeatOrder> seatOrders = ylrcSeatOrderService.list(lambdaQueryWrapper);
        List<Reading> readings =  ylrcReadingService.getRoom(readingtype_id);
        readings = readings.stream().map(o -> {
            Reading reading = o;
            Integer rest = reading.getRest();
            for(YlrcSeatOrder seatOrder:seatOrders){
                if(reading.getId()==seatOrder.getReadingRoomId()){
                    rest--;
                }
            }
            reading.setRest(rest);
            return reading;
        }).collect(Collectors.toList());
        return readings;
    }

    @GetMapping("/info")
    public YlrcReading info(Integer id){

        return ylrcReadingService.getById(id);
    }
}
