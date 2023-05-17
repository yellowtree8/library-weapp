package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.common.QueryPageParam;
import com.example.library.entity.User;
import com.example.library.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping
    public List<User> list(){
        return userService.list();
    }
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return userService.save(user);
    }
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }

    @PostMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    @GetMapping("/search")
    public List<User> search(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getNickname,user.getNickname());
        return userService.list(lambdaQueryWrapper);
    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){
        System.out.println(query.getPageNum());
        System.out.println(query.getPageSize());
        System.out.println(query.getParams());
        Page<User> page = new Page<>(1,2);
        IPage result = userService.page(page);
        System.out.println(result.getTotal());
        return result.getRecords();
    }

    @GetMapping("/right")
    public List<User> hello(){
        return userService.list();
    }

    @GetMapping("/error")
    public int error(){
        return 9/0;
    }
}
