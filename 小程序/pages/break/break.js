// pages/break/break.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    breakList: [],
    cardnum: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  onDelete(e){
    var that = this
    console.log(e)
    var orderId = e.currentTarget.dataset.orderid
    wx.request({
      url: "http://47.97.167.59:9090/card-order/getnum?cardId=2",
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
          title: `你还有${that.data.cardnum}张违规记录消除卡，确定要消除吗`,
          content: '',
          complete: (res) => {
            if (res.confirm) {
              if(that.data.cardnum==0){
                wx.showToast({
                  title: '没有可用的消除卡',
                  icon: "none"
                })
              }else{
                wx.request({
                  url: `http://47.97.167.59:9090/ylrc-seat-order/delete?orderId=${orderId}`,
                  method: 'POST',
                  header: {
                    'content-type': 'application/json' 
                  },
                  success: function(res){
                    var data = res.data.data
                    console.log(data)
                  },
                  fail: function(err){
                    console.log(err,'shibai');
                  }
                })
      
                // 将卡设置为已使用
                wx.request({
                  url: "http://47.97.167.59:9090/card-order/usecard?cardId=2",
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
    var that = this;
    var student_id = wx.getStorageSync('userId')

    wx.request({
      url: `http://47.97.167.59:9090/ylrc-seat-order/getbreak?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        data=data.reverse()
        console.log(data)
        that.setData({
          breakList: data
        })
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
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