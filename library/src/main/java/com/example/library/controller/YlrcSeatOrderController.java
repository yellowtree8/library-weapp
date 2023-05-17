package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.example.library.entity.*;
import com.example.library.result.ResultData;
import com.example.library.service.IYlrcSeatOrderService;
import com.example.library.service.IYlrcSeatService;
import com.example.library.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-23
 */
@RestController
@RequestMapping("/ylrc-seat-order")
public class YlrcSeatOrderController {
    @Autowired
    private IYlrcSeatOrderService ylrcSeatOrderService;
    @Autowired
    private IYlrcSeatService ylrcSeatService;

    @PostMapping("/order")
    public ResultData<Object> order(@RequestBody OrderBody order) {
        Long currentDay = DateUtil.day(new Date()).getTime();
        Date currentTime = new Date();
        QueryWrapper<YlrcSeatOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("student_id", order.getStudentId());
        if (currentDay == DateUtil.day(DateUtil.millisecondToDate(order.getSubscribeTime())).getTime() && order.getTimeCode() == 1 && currentTime.getHours() >= 12) {
            return ResultData.fail(402, "时间已过，请选择其他时间");
        }

        UserOrder stuIsOrdered = ylrcSeatOrderService.stuIsOrdered(order.getStudentId(), currentDay);
        if (Objects.nonNull(stuIsOrdered)) {
            return ResultData.fail(401, "你已经预约过了");
        }

        YlrcSeatOrder seatOrder = new YlrcSeatOrder();
        seatOrder.setCreateTime(currentTime);
        seatOrder.setUpdateTime(currentTime);
        seatOrder.setTimeCode(order.getTimeCode());
        seatOrder.setSubscribeTime(DateUtil.millisecondToDate(order.getSubscribeTime()));
        seatOrder.setSeatId(order.getSeatId());
        seatOrder.setStudentId(order.getStudentId());
        seatOrder.setReadingRoomId(order.getReadingRoomId());
        seatOrder.setState(0);
        ylrcSeatOrderService.save(seatOrder);
        return ResultData.success(true);
    }

    @GetMapping("/getorder")
    public List<UserOrder> getorder(long student_id) {
        long currentDay = DateUtil.day(new Date()).getTime();
        List<UserOrder> userOrders = ylrcSeatOrderService.getorder(student_id);
        UserOrder stuIsOrdered = ylrcSeatOrderService.stuIsOrdered(student_id, currentDay);
        if (!Objects.isNull(stuIsOrdered)) {
            userOrders = userOrders.stream().map(o -> {

                UserOrder userOrder = o;
                if (userOrder.getSubscribeTime().getTime() == stuIsOrdered.getSubscribeTime().getTime() && userOrder.getTimeCode() == stuIsOrdered.getTimeCode()) {
                    userOrder.setStatus(2);
                }
                return userOrder;
            }).collect(Collectors.toList());
        }
        return userOrders;
    }

    @GetMapping("/getNowOrder")
    public UserOrder getNowOrder(long student_id) {
        long currentDay = DateUtil.day(new Date()).getTime();

        UserOrder stuIsOrdered = ylrcSeatOrderService.stuIsOrdered(student_id, currentDay);
        return stuIsOrdered;
    }

    @PostMapping("/delete")
    public ResultData<Boolean> deleteOrder(Integer orderId) {
//        QueryWrapper<YlrcSeatOrder> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("student_id",student_id);
        ylrcSeatOrderService.removeById(orderId);

        return ResultData.success(true);
    }

    @PostMapping("/change")
    public ResultData<Boolean> changeState(@RequestParam(name = "orderId", required = true) Long orderId) {
        long currentDay = DateUtil.day(new Date()).getTime();


        YlrcSeatOrder seatOrder = ylrcSeatOrderService.getById(orderId);
        int pm = DateUtil.isPM(); //判断当前时间是否为下午，0为上午 1为下午
        if (currentDay != DateUtil.day(seatOrder.getSubscribeTime()).getTime() || (pm == 0 && seatOrder.getTimeCode() == 2)) {
            return ResultData.fail(-1001, "还没到签到时间");
        }
        if (seatOrder.getState() == 0) {
            seatOrder.setState(1);
            seatOrder.setUpdateTime(new Date());
        } else if (seatOrder.getState() == 1) {
            seatOrder.setState(2);
            seatOrder.setLeaveTime(new Date());
        } else if (seatOrder.getState() == 2) {
            seatOrder.setState((1));
            if (Objects.nonNull(seatOrder.getDuration())) {
                seatOrder.setDuration(seatOrder.getDuration() + new Date().getTime() - seatOrder.getLeaveTime().getTime());
            } else {
                seatOrder.setDuration(new Date().getTime() - seatOrder.getLeaveTime().getTime());
            }
        }

        ylrcSeatOrderService.updateById(seatOrder);
        return ResultData.success(true);
    }

    @PostMapping("/autoOrder")
    public ResultData autoOrder(@RequestParam(name = "student_id", required = true) Long student_id,
                                @RequestParam(name = "subscribe_time", required = true) Long subscribe_time,
                                @RequestParam(name = "timecode", required = true) int timecode) {
        long currentDay = DateUtil.day(new Date()).getTime();
        Date currentTime = new Date();
        LambdaQueryWrapper<YlrcSeatOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YlrcSeatOrder::getSubscribeTime, DateUtil.millisecondToDate(subscribe_time))
                .eq(YlrcSeatOrder::getTimeCode, timecode);
        if (currentTime.getDate() == DateUtil.millisecondToDate(subscribe_time).getDate() && timecode == 1 && currentTime.getHours() >= 12) {
            return ResultData.fail(402, "时间已过，请选择其他时间");
        }

        UserOrder stuIsOrdered = ylrcSeatOrderService.stuIsOrdered(student_id, currentDay);
        if (Objects.nonNull(stuIsOrdered)) {
            return ResultData.fail(401, "你已经预约过了");
        }
        List<YlrcSeatOrder> orderedSeats = ylrcSeatOrderService.list(lambdaQueryWrapper);
        List<YlrcSeat> seats = ylrcSeatService.list();
        Boolean outflag = false;
        for (YlrcSeat seat : seats) {
            Boolean flag = true;
            if (Objects.nonNull(orderedSeats)) {
                for (YlrcSeatOrder orderedSeat : orderedSeats) {
                    if (orderedSeat.getSeatId() == seat.getId())
                        flag = false;
                }
            }
            if (flag) {
                YlrcSeatOrder seatOrder = new YlrcSeatOrder();
                seatOrder.setCreateTime(currentTime);
                seatOrder.setUpdateTime(currentTime);
                seatOrder.setTimeCode(timecode);
                seatOrder.setSubscribeTime(DateUtil.millisecondToDate(subscribe_time));
                seatOrder.setSeatId(seat.getId());
                seatOrder.setStudentId(student_id);
                seatOrder.setReadingRoomId(seat.getReadingRoomId());
                seatOrder.setState(0);
                outflag = true;
                ylrcSeatOrderService.save(seatOrder);
                break;
            }
        }
        if (outflag) {
            return ResultData.success(true);
        } else {
            return ResultData.fail(-1000, "没有空余座位了");
        }
    }

    @GetMapping("/getbreak")
    public List<UserOrder> getbreak(@RequestParam(name = "student_id", required = true) Long student_id) {
        long currentDay = DateUtil.day(new Date()).getTime();
        List<UserOrder> userOrders = ylrcSeatOrderService.getbreak(student_id);
        UserOrder stuIsOrdered = ylrcSeatOrderService.stuIsOrdered(student_id, currentDay);
        List<UserOrder> last = new ArrayList<>();
        for (int i = 0, size = userOrders.size(); i < size; i++) {
            UserOrder o = userOrders.get(i);

            if (o.getState() == 1) {
                if ((o.getUpdateTime().getHours() > 9 && o.getTimeCode() == 1) || (o.getUpdateTime().getHours() > 13 && o.getTimeCode() == 2)) {
                    o.setStatus(4);  //迟到一个小时签到，状态为4
                    last.add(o);
                } else {

                }
            } else if (o.getState() == 0) {
                if (Objects.nonNull(stuIsOrdered) && o.getOrderId() == stuIsOrdered.getOrderId()) {

                } else {
                    o.setStatus(2); // 如果没有签到，状态为2
                    last.add(o);
                }

            } else if (o.getState() == 2) {
                if (Objects.nonNull(stuIsOrdered) && o.getOrderId() == stuIsOrdered.getOrderId()) {

                } else {
                    o.setStatus(3);
                    last.add(o);
                }// 暂离后没有回来，状态为3
            }
        }
        return last;
    }

    @GetMapping("getlearn")
    private TimeCount getLearn(@RequestParam(name = "student_id", required = true) Long student_id) {
        LambdaQueryWrapper<YlrcSeatOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YlrcSeatOrder::getStudentId, student_id)
                .between(YlrcSeatOrder::getSubscribeTime,
                        LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<YlrcSeatOrder> months = ylrcSeatOrderService.list(lambdaQueryWrapper);

        LambdaQueryWrapper<YlrcSeatOrder> l2 = new LambdaQueryWrapper<>();
        l2.eq(YlrcSeatOrder::getStudentId, student_id)
                .between(YlrcSeatOrder::getSubscribeTime,
                        LocalDateTime.now().minusWeeks(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<YlrcSeatOrder> weeks = ylrcSeatOrderService.list(l2);

        LambdaQueryWrapper<YlrcSeatOrder> l3 = new LambdaQueryWrapper<>();
        l3.eq(YlrcSeatOrder::getStudentId, student_id)
                .eq(YlrcSeatOrder::getSubscribeTime,
                        LocalDateTime.now().withHour(8).withMinute(0).withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<YlrcSeatOrder> days = ylrcSeatOrderService.list(l3);

        TimeCount timeCount = new TimeCount();
        Long daydur = 0L;
        Long weekdur = 0L;
        Long monthdur = 0L;
        long currentDay = DateUtil.day(new Date()).getTime();
        UserOrder stuIsOrdered = ylrcSeatOrderService.stuIsOrdered(student_id, currentDay);
        Long curdur = 0L;
        for (YlrcSeatOrder day : days) {
            if (Objects.nonNull(stuIsOrdered)&&day.getId() == stuIsOrdered.getOrderId()) {
                if (day.getState() == 1) {
                    daydur = daydur + new Date().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                    curdur = curdur + new Date().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                    weekdur = weekdur + new Date().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                    monthdur = monthdur + new Date().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                } else if (day.getState() == 2) {
                    daydur = daydur + day.getLeaveTime().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                    curdur = curdur + day.getLeaveTime().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                    weekdur = weekdur + day.getLeaveTime().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                    monthdur = monthdur + day.getLeaveTime().getTime() - day.getUpdateTime().getTime() - day.getDuration();
                }
            } else {
                if (day.getState() == 1) {
                    if (day.getTimeCode() == 1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(day.getUpdateTime());
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Long noon = calendar.getTime().getTime();
                        daydur = daydur + noon - day.getUpdateTime().getTime() - day.getDuration();
                    } else if (day.getTimeCode() == 2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(day.getUpdateTime());
                        calendar.set(Calendar.HOUR_OF_DAY, 22);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Long night = calendar.getTime().getTime();
                        daydur = daydur + night - day.getUpdateTime().getTime() - day.getDuration();
                    }
                }
            }
        }

        for (YlrcSeatOrder week : weeks) {
            if (Objects.isNull(stuIsOrdered)||week.getId() != stuIsOrdered.getOrderId()) {
                if (week.getState() == 1) {
                    if (week.getTimeCode() == 1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(week.getUpdateTime());
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Long noon = calendar.getTime().getTime();
                        weekdur = weekdur + noon - week.getUpdateTime().getTime() - week.getDuration();
                    } else if (week.getTimeCode() == 2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(week.getUpdateTime());
                        calendar.set(Calendar.HOUR_OF_DAY, 22);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Long night = calendar.getTime().getTime();
                        weekdur = weekdur + night - week.getUpdateTime().getTime() - week.getDuration();
                    }
                }
            }
        }

        for (YlrcSeatOrder month : months) {
            if (Objects.isNull(stuIsOrdered)||month.getId() != stuIsOrdered.getOrderId()) {
                if (month.getState() == 1) {
                    if (month.getTimeCode() == 1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(month.getUpdateTime());
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Long noon = calendar.getTime().getTime();
                        monthdur = monthdur + noon - month.getUpdateTime().getTime() - month.getDuration();
                    } else if (month.getTimeCode() == 2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(month.getUpdateTime());
                        calendar.set(Calendar.HOUR_OF_DAY, 22);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Long night = calendar.getTime().getTime();
                        monthdur = monthdur + night - month.getUpdateTime().getTime() - month.getDuration();
                    }
                }
            }
        }
        timeCount.setCur(curdur);
        timeCount.setDay(daydur);
        timeCount.setWeek(weekdur);
        timeCount.setMonth(monthdur);
        return timeCount;
    }



}
