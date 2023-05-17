// pages/subscribe/subscribe.js

Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    lc: 0,
    place: "",
    room_id: 0,
    option: {},
    seat: [],
    firstid: 0,
    timecode: 1,
    choosestamp: '',
    userId: 0,
  },
  // showPopup(e){
  //   this.setData({
  //      show: true,
  //      });
  // },
  /**
   * 生命周期函数--监听页面加载
   */
  onSeat(e){
    console.log(e)
    var that = this
    var seat = that.data.seat
    var xaxis = e.currentTarget.dataset.xaxis -1
    var yaxis = e.currentTarget.dataset.yaxis -1
    wx.request({
      url: "http://47.97.167.59:9090/ylrc-seat-order/order",
      method: 'POST',
      data: {
        subscribeTime: that.data.choosestamp,
        timeCode: that.data.timecode,
        readingRoomId: that.data.room_id,
        seatId: e.currentTarget.dataset.seatid,
        studentId: wx.getStorageSync('userId')
      },
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
          seat[xaxis][yaxis].status = 2
          that.setData({
            seat: seat
          })
        }  else{
          wx.showToast({
            title: data.msg,
            icon: 'none'
          })
        }
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })

  },
  onLoad(options) {
    var that = this;

    this.setData({
      room_id: parseInt(options.id),
      lc: options.lc,
      timecode: parseInt(options.timecode),
      choosestamp: parseInt(options.choosestamp),
      // userId: JSON.parse(wx.getStorageSync('user')).
    })

    var room_id = that.data.room_id
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-reading/info?id=${room_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/xml' 
      },
      success: function(res){
        var data = res.data.data
        that.setData({
          option: data
        })
        console.log(data)
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
// 请求座位信息
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-seat/getseat?room_id=${that.data.room_id}&timecode=${that.data.timecode}&date=${that.data.choosestamp}`,
      method: 'GET',
      header: {
        'content-type': 'application/xml' 
      },
      success: function(res){
        var data = res.data.data
        var arr = []
        arr[0] = []
        var index = 0
        for(var i = 0;i<data.length;i++){
          if(data[i].xaxis!=index+1){ 
            index++
            arr[index] = new Array()
          }
          arr[index].push(data[i]) 
        } 
        that.setData({
          seat: arr,
          firstid: data[0].id
        })
        console.log(that.data.seat) 
       
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