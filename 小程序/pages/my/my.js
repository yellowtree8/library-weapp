// pages/my/my.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    info: {},
    islogin: false,
    learn_time: []
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
    var that = this
    var student_id = wx.getStorageSync('userId')
    var userinfo = wx.getStorageSync('user')
    if(userinfo.length==0){
      this.setData({
        islogin: false
      })
    }else{
      this.setData({
        islogin: true,
        info: JSON.parse(userinfo)
      })
      console.log(this.data.info,11)
    }
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-seat-order/getlearn?student_id=${student_id}`,
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
  onUserinfo(){
    wx.navigateTo({
      url: `/pages/userinfo/userinfo`
    })
  },
  onRecord(){
    wx.navigateTo({
      url: '/pages/record/record',
    })
  },
  onLogin(){
    wx.navigateTo({
      url: '/pages/login/login'
    })
  },
  onLogout(){
    wx.showModal({
      title: '确定要退出登录吗',
      content: '',
      complete: (res) => {
        if (res.cancel) {
          
        }
    
        if (res.confirm) {
          wx.removeStorageSync('user')
          this.setData({
            islogin: false
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