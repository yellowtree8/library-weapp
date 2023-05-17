// pages/shopping/shopping.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cardList: [],
    credit: null
  },
  // 兑换卡片
  onBuy(e){
    
    var tool_id = e.currentTarget.dataset.id
    var title = e.currentTarget.dataset.title
    var cost = e.currentTarget.dataset.cost
    var that = this
    var student_id = wx.getStorageSync('userId')
    wx.showModal({
      title: `确定要花费${cost}积分兑换${title}吗`,
      content: `当前积分：${that.data.credit}`,
      complete: (res) => { 
        if (res.confirm) {
          wx.request({
            url: `http://localhost:9090/card-order/buy?student_id=${student_id}&tool_id=${tool_id}&status=1`,
            method: 'POST',
            header: {
              'content-type': 'application/json' 
            },
            success: function(res){
              var data = res.data
              console.log(data)
              if(data.data){
                wx.showToast({
                  title: '兑换成功',
                })
                that.onShow()
              }else if(data.code==-102){
                wx.showToast({
                  title: data.msg,
                  icon: "error"
                })
              }
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
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-student/credit?student_id=${student_id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        that.setData({
          credit: data
        })
        console.log(data)
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
    wx.request({
      url: "http://47.97.167.59:9090/card/list",
      method: 'GET',
      header: {
        'content-type': 'application/json' 
      },
      success: function(res){
        var data = res.data.data
        that.setData({
          cardList: data
        })
        console.log(data)
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