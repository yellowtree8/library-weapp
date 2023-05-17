package com.example.library.service.impl;

import com.example.library.entity.YlrcStudent;
import com.example.library.mapper.YlrcStudentMapper;
import com.example.library.service.IYlrcStudentService;
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
 * @since 2023-03-23
 */
@Service
public class YlrcStudentServiceImpl extends ServiceImpl<YlrcStudentMapper, YlrcStudent> implements IYlrcStudentService {
    @Resource
    private YlrcStudentMapper ylrcStudentMapper;
    @Override
    public List<YlrcStudent> stuid(Integer user_id){
        return ylrcStudentMapper.stuid(user_id);
    }
}
