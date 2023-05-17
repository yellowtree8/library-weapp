package com.example.library.controller;


import com.example.library.entity.Card;
import com.example.library.service.ICardService;
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
 * @since 2023-03-31
 */
@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private ICardService cardService;
    @GetMapping("/list")
    public List<Card> list(){
        return cardService.list();
    }
}
