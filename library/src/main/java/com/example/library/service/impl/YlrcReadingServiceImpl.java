package com.example.library.service.impl;

import com.example.library.entity.Reading;
import com.example.library.entity.YlrcReading;
import com.example.library.mapper.YlrcReadingMapper;
import com.example.library.service.IYlrcReadingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-03-19
 */
@Service
public class YlrcReadingServiceImpl extends ServiceImpl<YlrcReadingMapper, YlrcReading> implements IYlrcReadingService {
    @Resource
    private YlrcReadingMapper ylrcReadingMapper;
    @Override
    public List<Reading> getRoom(Integer readingtype_id){
        return ylrcReadingMapper.getRoom(readingtype_id);
    }
}
