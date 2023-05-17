// index.js
// 获取应用实例
const app = getApp()
var util = require('../../utils/util')
Page({
  data: {
    unread: 0,
    show: false,
    issubscribed: true,
    innumber: 10,
    outnumber: 10,
    order: null,
    isorder: false,
    mainActiveIndex: 0,
    activeId: null,
    timecode: 1,
    todaystamp: '',
    tomostamp: '',
    choosestamp: '',
    items: [{
      text: "今天",
      children: [
        {
          text: "上午",
          id: 1
        },
        {
          text: "下午",
          id: 2
        }
      ]
    },{
      text: "明天",
      children: [
        {
          text: "上午",
          id: 1
        },
        {
          text: "下午",
          id: 2
        }
      ]
    }]
  },
  onClickNav({ detail = {} }) {
    this.setData({
      mainActiveIndex: detail.index || 0,
    });

  },
  onClickItem({ detail = {} }) {
    const activeId = this.data.activeId === detail.id ? null : detail.id;
    var choosestamp  = this.data.mainActiveIndex?this.data.tomostamp:this.data.todaystamp;
    this.setData({
      activeId :activeId,
      title: util.formatDate(new Date(choosestamp))+'\xa0'+'\xa0'+detail.text,
      timecode: detail.id,
      choosestamp: choosestamp
    });


   
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  showPopup() {
    this.setData({ show: true });
  },

  onClose() {
    this.setData({ show: false });
  },
  onseat(){
    wx.switchTab({
      url: '/pages/choose-seat/choose-seat',
    })
  },
  onShopping(){
    wx.navigateTo({
      url: '/pages/shopping/shopping',
    })
  },
  onSure(){
    var that = this;
    var student_id = wx.getStorageSync('userId')
    this.onClose()
  
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-seat-order/autoOrder?student_id=${student_id}&subscribe_time=${that.data.choosestamp}&timecode=${that.data.timecode}`,
      method: 'POST',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data
        console.log(data)
        if(data.code == 200){
          wx.showToast({
            title: '预约成功',
            icon: 'none'
          })
         
        }  else{
          wx.showToast({
            title: data.msg,
            icon: 'none'
          })
        }
        that.onShow();
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })

  },
  onCheckout(){
    var that = this
    wx.showModal({
      title: '确定要退座吗',
      content: '',
      complete: (res) => {
        if (res.cancel) {
          
        }
    
        if (res.confirm) {
          wx.request({
            url: `http://47.97.167.59:9090/ylrc-seat-order/delete?orderId=${that.data.order.orderId}`,
            method: 'POST',
            header: {
              'content-type': 'application/json' 
            },
            success: function(res){
              var data = res.data.data
              console.log(data)
              that.setData({
                isorder: false
              })
              that.onShow() 
            },
            fail: function(err){
              console.log(err,'shibai');
            }
          })
        }
      }
    })
  },
  onLeave(){
    wx.showModal({
      title: '暂离座位回座时需要重新扫码签回，确定要暂离吗',
      complete: (res) => {
        if (res.confirm) {
          var that = this;
          var orderId = that.data.order.orderId

          wx.request({
            url: `http://47.97.167.59:9090/ylrc-seat-order/change?orderId=${orderId}`,
            method: 'POST',
            header: {
              'content-type': 'application/json' 
            },
            success: function(res){
              var data = res.data.data
              console.log(data)
              that.onShow()
            },
            fail: function(err){
              console.log(err,'shibai');
            }
          })  
        }
      }
    })
  },
  onCheckin(){
    var that = this;
    var order = that.data.order
    wx.scanCode({
      success: function(res){
        var data = JSON.parse(res.result)

        var readingRoomId = data.readingRoom.id
        var xAxis = data.xAxis
        var yAxis = data.yAxis
        console.log(JSON.parse(res.result))
        if(order){
          if(data.xAxis==order.xaxis&&data.yAxis==order.yaxis&&data.readingRoom.name==order.name){
            var orderId = that.data.order.orderId
            wx.request({
              url: `http://47.97.167.59:9090/ylrc-seat-order/change?orderId=${orderId}`,
              method: 'POST',
              header: {
                'content-type': 'application/json' 
              },
              success: function(res){
                var a = res.data
                console.log(a,11)
                that.onShow()
                wx.showToast({
                  title: a.msg,
                  icon: "none"
                })
              },
              fail: function(err){
                console.log(err,'shibai');
              }
            })  
          } else{
            wx.showToast({
              title: '这不是您的位置',
              icon: " none"
            })
          }
        }
        wx.navigateTo({
          url: `/pages/checkin/checkin?readingRoomId=${readingRoomId}&xAxis=${xAxis}&yAxis=${yAxis}&name=${data.readingRoom.name}&lc=${data.readingRoom.readingType.name}`,
        })
      },
      fail: function(err){
        console.log(err,111)
      }
    })
  },
  onRecord(){
    wx.navigateTo({
      url: '/pages/record/record',
    })
  },
  onChat(){
    wx.navigateTo({
      url: '/pages/talk/talk',
    })
  },
  onBreak(){
    wx.navigateTo({
      url: '/pages/break/break',
    })
  },
  onLoad() {
        // 加载时间
        var that = this
        var a = Date.parse(new Date());
        var todaystamp = util.formateight(a)
        var today = util.formatDate(new Date());
    
        var b = Date.parse(new Date())+24*60*60*1000 
        var tomostamp = util.formateight(b)
        var tomo = util.formatDate(new Date(tomostamp))
        var obj = that.data.items
        obj[0].text = today+" 今天"
        obj[1].text = tomo+" 明天"
        that.setData({
          todaystamp: todaystamp,
          tomostamp: tomostamp,
          choosestamp: todaystamp,
          items: obj,
          title: today+'\xa0'+'\xa0'+'上午'
        })
  },
  onShow(){
    var that = this;
    var student_id = wx.getStorageSync('userId')

    wx.request({
      url: `http://47.97.167.59:9090/ylrc-seat-order/getNowOrder?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
          that.setData({
            order: data,
            isorder: data?true:false
          })
          console.log(data)
        },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
    wx.request({
      url: `http://47.97.167.59:9090/user-msg/unread?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
          that.setData({
            unread: data
          })
          console.log(data)
        },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  },

})
