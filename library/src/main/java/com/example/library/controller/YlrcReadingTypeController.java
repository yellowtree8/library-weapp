package com.example.library.controller;


import com.example.library.entity.YlrcReadingType;
import com.example.library.service.IYlrcReadingTypeService;
import com.example.library.service.impl.YlrcReadingTypeServiceImpl;
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
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/readingtype")
public class YlrcReadingTypeController {
    @Autowired
    private IYlrcReadingTypeService ylrcReadingTypeService;
    @GetMapping("/list")
    public List<YlrcReadingType> list(){
        return ylrcReadingTypeService.list();
    }
}
