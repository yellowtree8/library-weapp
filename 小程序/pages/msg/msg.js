// chat.js

Page({
  data: {
    inputValue: '', // 输入框的值
    studentId: null,
    userinfo: null,
    otherName: '',
    otherPic: '',
    otherId: null,
    chatLog: [ // 聊天记录
    ],
    toView: '' // 滚动到的位置
  },

  // 输入框内容改变事件
  onInput: function (event) {
    this.setData({
      inputValue: event.detail.value
    })
  },

  // 发送消息事件
  onSend: function () {
    var that = this
    let message = this.data.inputValue.trim()
    if (message) {
      wx.request({
        url: `http://47.97.167.59:9090/user-msg/sendMsg`,
        method: 'POST',
        data: {
          sendId: that.data.studentId,
          msg: message,
          acceptId: that.data.otherId,
          createTime: new Date(),
          secret: false 
        },
        header: {
          'content-type': 'application/json' 
        },
        success: function(res){
          var data = res.data.data
          console.log(data)
          wx.request({
            url: `http://47.97.167.59:9090/user-msg/getChat?student_id=${that.data.studentId}&send_id=${that.data.otherId}`,
            method: 'GET',
            header: {
              'content-type': 'application/json' 
            },
            success: function(res){
              var data = res.data.data
              var time=0;
              for(var i = 0;i<data.length;i++){
                if(new Date(data[i].createTime).getTime()-time>2*60*1000){
                  data[i].secret = true
                }
                time = new Date(data[i].createTime)
              }
              that.setData({
                chatLog: data,
                inputValue: '',
                toView: 'chatLog-' + (data.length - 1)
              })
            },
            fail: function(err){
              console.log(err,'shibai');
            }
          })
        },
        fail: function(err){
          console.log(err,'shibai');
        }
      })
      // this.setData({
      //   chatLog: chatLog,
      //   inputValue: '',
      //   toView: 'chatLog-' + (chatLog.length - 1)
      // })
    }
  },
  onShow(){

  },
  onLoad(options){
    var that = this
    var student_id = wx.getStorageSync('userId')
    var userinfo = wx.getStorageSync('user')
    wx.setNavigationBarTitle({ title: options.nickName }) 
    console.log(options)
    that.setData({
      otherName: options.nickName,
      otherPic: options.pic,
      otherId: options.sendId,
      studentId: student_id,
      userinfo: JSON.parse(userinfo)
    })

    var send_id = options.sendId


    wx.request({
      url: `http://47.97.167.59:9090/user-msg/getChat?student_id=${student_id}&send_id=${send_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        console.log(data)
        var time=0;
        for(var i = 0;i<data.length;i++){
          if(new Date(data[i].createTime).getTime()-time>2*60*1000){
            data[i].secret = true
          }
          time = new Date(data[i].createTime).getTime()
          console.log(time)
        }
        that.setData({
          chatLog: data,
          inputValue: '',
          toView: 'chatLog-' + (data.length - 1)
        })
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  }
})
