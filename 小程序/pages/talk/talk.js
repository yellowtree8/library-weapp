// pages/record/record.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    talkList: [],
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
      url: `http://47.97.167.59:9090/user-msg/getTalk?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        var temp;

        if(data){

          that.setData({
            talkList: data.reverse()
          })
          console.log(data)
        }
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  },
  onChat(e){
    var nickName = e.currentTarget.dataset.nickname
    var pic = e.currentTarget.dataset.pic
    var sendId = e.currentTarget.dataset.sendid
    console.log(e)
    wx.navigateTo({
      url: `/pages/msg/msg?nickName=${nickName}&pic=${pic}&sendId=${sendId}`
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