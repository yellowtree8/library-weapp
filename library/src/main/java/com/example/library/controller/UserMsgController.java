package com.example.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.library.entity.Talk;
import com.example.library.entity.User;
import com.example.library.entity.UserMsg;
import com.example.library.result.ResultData;
import com.example.library.service.IUserMsgService;
import com.example.library.service.IYlrcStudentService;
import com.example.library.service.IYlrcUserService;
import com.sun.jdi.PrimitiveValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-04-26
 */
@RestController
@RequestMapping("/user-msg")
public class UserMsgController {
    @Autowired
    private IUserMsgService userMsgService;


    @Autowired
    private IYlrcUserService ylrcUserService;
    @Autowired
    private IYlrcStudentService ylrcStudentService;

    // 发送信息
    @PostMapping("/send")
    public ResultData<Boolean> send(@RequestBody UserMsg userMsg){
        Date currentDate = new Date();
        userMsg.setCreateTime(currentDate);
        userMsg.setRead(0);
        userMsgService.save(userMsg);
        return ResultData.success(true);
    }


    @GetMapping("unread")
    public int unread(@RequestParam(name = "student_id",required = true)Long student_id){
        LambdaQueryWrapper<UserMsg> l1 = new LambdaQueryWrapper<>();
        l1.eq(UserMsg::getAcceptId,student_id).eq(UserMsg::getRead,0);
        return userMsgService.count(l1);
    }
    @GetMapping("/getTalk")
    public List<Talk> getTalk(@RequestParam(name = "student_id",required = true)Long student_id){
        LambdaQueryWrapper<UserMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserMsg::getSendId,student_id).or().eq(UserMsg::getAcceptId,student_id);
        List<UserMsg> userMsgs = userMsgService.list(queryWrapper);
        List<Talk> talkList = new ArrayList<>();
        Set set = new HashSet<>();
        for (int i = userMsgs.size()-1;i>=0;i--){
            UserMsg userMsg = userMsgs.get(i);
            if(userMsg.getSendId()!=student_id){
                LambdaQueryWrapper<UserMsg> l1 = new LambdaQueryWrapper<>();
                l1.eq(UserMsg::getSendId,userMsg.getSendId()).eq(UserMsg::getAcceptId,student_id).eq(UserMsg::getRead,0);

                if(!set.contains(userMsg.getSendId())){
                    set.add(userMsg.getSendId());
                    Talk talk = new Talk();
                    talk.setMsg(userMsg.getMsg());
                    talk.setSecret(userMsg.getSecret());
                    talk.setSendId(userMsg.getSendId());
                    talk.setCreateTime(userMsg.getCreateTime());
                    talk.setNickName(ylrcUserService.getById(ylrcStudentService.getById(userMsg.getSendId()).getUserId()).getNickName());
                    talk.setPic(ylrcUserService.getById(ylrcStudentService.getById(userMsg.getSendId()).getUserId()).getHeadPic());
                    talk.setUnread(userMsgService.count(l1));
                    talkList.add(talk);
                }
            }
            if(userMsg.getAcceptId()!=student_id){
                LambdaQueryWrapper<UserMsg> l2 = new LambdaQueryWrapper<>();
                l2.eq(UserMsg::getSendId,userMsg.getAcceptId()).eq(UserMsg::getAcceptId,student_id).eq(UserMsg::getRead,0);
                if(!set.contains(userMsg.getAcceptId())){
                    set.add(userMsg.getAcceptId());
                    Talk talk = new Talk();
                    talk.setMsg(userMsg.getMsg());
                    talk.setSecret(userMsg.getSecret());
                    talk.setSendId(userMsg.getAcceptId());
                    talk.setCreateTime(userMsg.getCreateTime());
                    talk.setNickName(ylrcUserService.getById(ylrcStudentService.getById(userMsg.getAcceptId()).getUserId()).getNickName());
                    talk.setUnread(userMsgService.count(l2));
                    talkList.add(talk);
                }
            }
        }
        return talkList;
    }
    @GetMapping("getChat")
    public List<UserMsg> getChat(@RequestParam(name = "student_id",required = true)Long student_id,
                                 @RequestParam(name = "send_id",required = true)Long send_id){
        LambdaQueryWrapper<UserMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserMsg::getSendId,student_id).eq(UserMsg::getAcceptId,send_id)
                .or().eq(UserMsg::getSendId,send_id).eq(UserMsg::getAcceptId,student_id).orderByAsc(UserMsg::getId);
        LambdaQueryWrapper<UserMsg> l1 = new LambdaQueryWrapper<>();
        l1.eq(UserMsg::getAcceptId,student_id).eq(UserMsg::getSendId,send_id).eq(UserMsg::getRead,0);
        List<UserMsg> unreadList =userMsgService.list(l1);
        for(UserMsg userMsg:unreadList){
            userMsg.setRead(1);
            userMsgService.updateById(userMsg);
        }
        List<UserMsg> userMsgs = userMsgService.list(queryWrapper);
        return userMsgs;
    }

    @PostMapping("sendMsg")
    public ResultData<Boolean> sendMsg(@RequestBody UserMsg userMsg){
        userMsgService.save(userMsg);
        return ResultData.success(true);
    }
}
