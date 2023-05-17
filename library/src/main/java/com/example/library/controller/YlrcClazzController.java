package com.example.library.controller;


import com.example.library.entity.YlrcClazz;
import com.example.library.service.IYlrcClazzService;
import com.example.library.service.IYlrcStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/ylrc-clazz")
public class YlrcClazzController {
    @Autowired
    private IYlrcClazzService ylrcClazzService;

    @Autowired
    private IYlrcStudentService ylrcStudentService;

    @GetMapping("/getclass")
    public YlrcClazz getclass(Long student_id){
        Long classId = ylrcStudentService.getById(student_id).getClazzId();
        System.out.println(classId);
        System.out.println(ylrcClazzService.getById(classId));
        return ylrcClazzService.getById(classId);
    }
}
