// pages/board-detail/board-detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    myart: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var art_id = options.id
    wx.cloud.database().collection('tzgg').where({
      _id: art_id
    }).get()
    .then(res => {
      this.setData({
        myart: res.data[0]
      })
      console.log(this.data.myart)
    })
    .catch(err=>{
      console.log("请求失败",err)
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