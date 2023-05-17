// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    eye: false,
    number: "2019051488",
    password: "sb20010810"
  },

  // 密码可见函数
  oneye(){
    this.setData({
      eye: !this.data.eye
    })
  }, 
  numberInput(e){
    this.setData({
      number: e.detail.value
    })
  },
  passwordInput(e){
    this.setData({
      password: e.detail.value
    })
  },
  onLogin(){
    var that = this;
    const number = this.data.number
    const password = this.data.password
    if(number.length<6||number.length>15||password.length<6){
      wx.showToast({
        title: '格式错误',
        icon: 'error'
      })
      that.setData({
        number: '',
        password: ''
      })
      return null;
    }
    wx.request({
      url: 'http://47.97.167.59:9090/ylrc-user/login',
      method: 'POST',
      data: {
        username: number,
        password: password
      },
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        console.log(data)
        if(data==null){
          wx.showToast({
            title: '账号或密码出错',
            icon: 'error'
          })
          that.setData({
            number: '',
            password: ''
          })
        } else{
          wx.request({
            url: `http://47.97.167.59:9090/ylrc-student/stuid?user_id=${data.id}`,
            method: 'GET',
            header: {
              'content-type': 'application/json' 
            },
            success: function(res){
              var user_id = res.data.data
              wx.setStorageSync('userId', user_id)
            },
            fail: function(err){
              console.log(err,'shibai');
            }
          })
          wx.setStorageSync('user',JSON.stringify(data))

          wx.navigateBack(
          );
          wx.showToast({
            title: '登录成功',
            icon: "success"
          })
        }
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
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