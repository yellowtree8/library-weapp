package com.example.library.controller;


import com.example.library.entity.YlrcStudent;
import com.example.library.service.IYlrcStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-23
 */
@RestController
@RequestMapping("/ylrc-student")
public class YlrcStudentController {
    @Autowired
    private IYlrcStudentService ylrcStudentService;

    @GetMapping("/stuid")
    public Integer getid(Integer user_id){
        return ylrcStudentService.stuid(user_id).get(0).getId();
    }

    @GetMapping("/credit")
    public Integer getcredit(Long student_id){
        YlrcStudent student =  ylrcStudentService.getById(student_id);
        return student.getStudentCredits();
    }

}
