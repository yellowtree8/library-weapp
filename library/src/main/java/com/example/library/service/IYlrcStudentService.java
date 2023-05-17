package com.example.library.service;

import com.example.library.entity.YlrcStudent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-03-23
 */
public interface IYlrcStudentService extends IService<YlrcStudent> {

    List<YlrcStudent> stuid(Integer user_id);
}
