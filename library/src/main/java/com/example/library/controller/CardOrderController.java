package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.library.entity.*;
import com.example.library.result.ResultData;
import com.example.library.service.ICardOrderService;
import com.example.library.service.ICardService;
import com.example.library.service.IYlrcStudentService;
import com.example.library.service.IYlrcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @since 2023-04-01
 */
@RestController
@RequestMapping("/card-order")
public class CardOrderController {
    @Autowired
    private ICardOrderService cardOrderService;

    @Autowired
    private ICardService cardService;

    @Autowired
    private IYlrcStudentService ylrcStudentService;
    @PostMapping("/buy")
    public ResultData<Boolean> buy(@RequestParam(name = "student_id",required = true)Long student_id,
                                   @RequestParam(name = "tool_id",required = true)Long tool_id,
                                   @RequestParam(name = "status",required = true)Integer status){
        Date currentTime =new Date();
        Card card =  cardService.getById(tool_id);
        YlrcStudent user = ylrcStudentService.getById(student_id);
        Integer cost = card.getCost();
        Integer credit = user.getStudentCredits();
        if(status==1){
            if(cost>credit){
                return ResultData.fail(-102,"你的信用分不够");
            }
            user.setStudentCredits(credit-cost);
            ylrcStudentService.updateById(user);
            CardOrder cardOrder = new CardOrder();
            cardOrder.setStudentId(student_id);
            cardOrder.setToolId(tool_id);
            cardOrder.setStatus(1);
            cardOrder.setCreateTime(currentTime);
            cardOrder.setUpdateTime(currentTime);
            cardOrderService.save(cardOrder);
        }
        return ResultData.success(true);
    }

    @GetMapping("/ablecard")
    public List<CardNum> ablecard(@RequestParam(name = "status",required = true)Integer status,
                                  @RequestParam(name = "student_id",required = true)Long student_id){
        return cardOrderService.cardnum(status,student_id);
    }

    @GetMapping("/usedcard")
    public List<UsedCard> usedcard(@RequestParam(name = "student_id",required = true)Long student_id){

        return cardOrderService.usedcard(student_id);
    }

    @PostMapping("/delete")
    public ResultData<Boolean> deleteOrder(Integer cardId) {

        cardOrderService.removeById(cardId);

        return ResultData.success(true);
    }
    @GetMapping("/getnum")
    public Integer getnum(@RequestParam(name = "cardId",required = true)Long cardId){
        LambdaQueryWrapper<CardOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CardOrder::getToolId,cardId)
                .eq(CardOrder::getStatus,1);
        return cardOrderService.count(lambdaQueryWrapper);
    }

    @PostMapping("/usecard")
    public ResultData<Boolean> usecard(@RequestParam(name = "cardId",required = true)Long cardId){
        LambdaQueryWrapper<CardOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CardOrder::getToolId,cardId)
                .eq(CardOrder::getStatus,1);
        List<CardOrder> cardOrders = cardOrderService.list(lambdaQueryWrapper);
        if(Objects.isNull(cardOrders)){
            return ResultData.fail(-201,"剩余道具为0，请先兑换");
        }
        CardOrder cardOrder = cardOrders.get(0);
        cardOrder.setStatus(-1);
        cardOrder.setUpdateTime(new Date());
        cardOrderService.updateById(cardOrder);
        return ResultData.success(true);
    }
}
