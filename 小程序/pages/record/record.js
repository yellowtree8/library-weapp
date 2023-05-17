// pages/record/record.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    order: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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
      url: `http://47.97.167.59:9090/ylrc-seat-order/getorder?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        var temp;

        if(data){
          // for(var i = 0;i<data.length;i++){
          //   if(data[i].status==2){
          //     temp = data[0];
          //     data[0] = data[i]
          //     data[i] = temp;
          //   }
          // }
          that.setData({
            order: data.reverse()
          })
          console.log(data)
        }else{
          that.setData({
            isorder: false
          })
        }

      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  },
  onCheckout(e){
    var that = this
    var orderId = e.currentTarget.dataset.orderid
    wx.showModal({
      title: '确定要退座吗',
      content: '',
      complete: (res) => {
        if (res.confirm) {
          wx.request({
            url: `http://47.97.167.59:9090/ylrc-seat-order/delete?orderId=${orderId}`,
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