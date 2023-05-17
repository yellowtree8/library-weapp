package com.example.library.service.impl;

import com.example.library.entity.UserMsg;
import com.example.library.mapper.UserMsgMapper;
import com.example.library.service.IUserMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-04-26
 */
@Service
public class UserMsgServiceImpl extends ServiceImpl<UserMsgMapper, UserMsg> implements IUserMsgService {

}
