// pages/userinfo/userinfo.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    info: {},
    class: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var info = JSON.parse(wx.getStorageSync('user'))
    var student_id = wx.getStorageSync('userId')
    var that = this;
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-clazz/getclass?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        console.log(data)
        that.setData({
          class: data
        })
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })  
    that.setData({
      info: info
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