package com.example.library.controller;


import com.example.library.entity.Tzgg;
import com.example.library.service.ITzggService;
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
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/tzgg")
public class TzggController {
    @Autowired
    private ITzggService tzggService;
    @GetMapping
    public List<Tzgg> list(){
        return tzggService.list();
    }
}
