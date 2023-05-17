// pages/board/board.js
var util = require('../../utils/util')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    msgList: []
  },
  onDetail(e){
    wx.navigateTo({
      url: `/pages/board-detail/board-detail?id=${e.currentTarget.dataset.id}`

    })
    console.log(e.currentTarget.dataset.id)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    wx.cloud.database().collection('tzgg').get()
    .then(res => {
      var data = res.data
      console.log(data)
      this.setData({
        msgList: data
      })
      console.log(data)
    })
    .catch(err => {
      console.log('请求失败',err)
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