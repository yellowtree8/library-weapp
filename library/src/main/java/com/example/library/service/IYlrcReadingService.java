package com.example.library.service;

import com.example.library.entity.Reading;
import com.example.library.entity.YlrcReading;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-03-19
 */
public interface IYlrcReadingService extends IService<YlrcReading> {

    List<Reading> getRoom(Integer id);
}
