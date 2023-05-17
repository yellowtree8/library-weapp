package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.library.entity.YlrcUser;
import com.example.library.service.IYlrcUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/ylrc-user")
public class YlrcUserController {
    @Autowired
    private IYlrcUserService ylrcUserService;
    @PostMapping("login")
    public YlrcUser login (@RequestBody YlrcUser user){
        LambdaQueryWrapper<YlrcUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YlrcUser::getUsername,user.getUsername())
                .eq(YlrcUser::getPassword,user.getPassword());
        return ylrcUserService.getOne(lambdaQueryWrapper);
    }
}
