package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.library.entity.Chat;
import com.example.library.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IChatService chatService;
    @GetMapping("/getChat")
    public List getchat(@RequestParam(name = "student_id",required = true)Long student_id){
        List<ArrayList> chat = new ArrayList<>();
        QueryWrapper<Chat> queryWrapper = new QueryWrapper();
        queryWrapper.eq("sendId",student_id).or().eq("acceptId",student_id);
        return chatService.list(queryWrapper);
    }
}
