// pages/checkin/checkin.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    readingRoomId: null,
    xAxis: '',
    yAxis: '',
    seatuser: '',
    name: '',
    lc: '',
    learn_time: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that = this
    that.setData({
      readingRoomId: options.readingRoomId,
      xAxis: options.xAxis,
      yAxis: options.yAxis,
      name: options.name,
      lc: options.lc
    })
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-seat/seatinfo?room_id=${options.readingRoomId}&xAxis=${options.xAxis}&yAxis=${options.yAxis}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        console.log(data)
        that.setData({
          seatuser: data
        })
        wx.request({
          url: `http://47.97.167.59:9090/ylrc-seat-order/getlearn?student_id=${data.studentId}`,
          method: 'GET',
          header: {
            'content-type': 'application/json' 
          },
          success: function(res){
            var data = res.data.data
            console.log(data)
            that.setData({
              learn_time: data
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

  },
  onChat(){
    var that = this
    wx.request({
      url: "http://47.97.167.59:9090/card-order/getnum?cardId=1",
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        that.setData({
          cardnum: data
        })
        wx.showModal({
          title: `你还有${that.data.cardnum}张小纸条，确定要使用吗`,
          content: '',
          complete: (res) => {
            if (res.confirm) {
              if(that.data.cardnum==0){
                wx.showToast({
                  title: '没有可用的消除卡',
                  icon: "none"
                })
              }else{
                // 将卡设置为已使用
                wx.request({
                  url: "http://47.97.167.59:9090/card-order/usecard?cardId=1",
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
                wx.navigateTo({
                  url: `/pages/msg/msg?nickName=${that.data.seatuser.nickName}&pic=${that.data.seatuser.headPic}&sendId=${that.data.seatuser.studentId}`,
                })
              }

            }
          }
        })
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})