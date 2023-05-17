// pages/mycard/mycard.js
var util = require('../../utils/util')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    active: 0,
    ableList: [],
    disableList: []
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
      url: `http://47.97.167.59:9090/card-order/ablecard?status=1&student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        that.setData({
          ableList: data
        })
        console.log(that.data.ableList)
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
    wx.request({
      url: `http://47.97.167.59:9090/card-order/usedcard?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        that.setData({
          disableList: data
        })
        console.log(that.data.disableList)
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  },
  onDelete(e){
    var that = this
    var cardId = e.currentTarget.dataset.cardid
    console.log(cardId)
    console.log(e)
    wx.showModal({
      title: '确定要删除该条记录吗',
      content: '',
      complete: (res) => {
        if (res.confirm) {
          wx.request({
            url: `http://47.97.167.59:9090/card-order/delete?cardId=${cardId}`,
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