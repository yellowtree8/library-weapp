var util = require('../../utils/util')
// pages/choose-seat/choose-seat.js
var util = require('../../utils/util')
Page({

  data: {
    option1: [],
    place: [],
    value1: 0,
    avtive: {},
    placetaped: {},
    show: false,
    value2: 0,
    mainActiveIndex: 0,
    activeId: null,
    todaystamp: '',
    tomostamp: '',
    choosestamp: '',
    timecode: 1,
    title: '',
    items: [{
      text: "今天",
      children: [
        {
          text: "上午",
          id: 1
        },
        {
          text: "下午",
          id: 2
        }
      ]
    },{
      text: "明天",
      children: [
        {
          text: "上午",
          id: 1
        },
        {
          text: "下午",
          id: 2
        }
      ]
    }]
  },
  onClickNav({ detail = {} }) {
    this.setData({
      mainActiveIndex: detail.index || 0,
    });

  },
  onClickItem({ detail = {} }) {
    const activeId = this.data.activeId === detail.id ? null : detail.id;
    var choosestamp  = this.data.mainActiveIndex?this.data.tomostamp:this.data.todaystamp;
    this.setData({
      activeId :activeId,
      title: util.formatDate(new Date(choosestamp))+'\xa0'+'\xa0'+detail.text,
      timecode: detail.id,
      choosestamp: choosestamp
    });
    this.selectComponent('#timemenu').toggle();
    var that = this
    var readingtype_id = that.data.active.value
 
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-reading/list?readingtype_id=${readingtype_id}&subscribe_time=${that.data.choosestamp}&timecode=${that.data.timecode}`,
      method: 'GET',
      header: {
        'content-type': 'application/xml' 
      },
      success: function(res){
        var data = res.data.data

        that.setData({
          place: data,
        })
        console.log(data)
      },
      fail: function(err){
        console.log(err,'shibai');
      }
    })
  },
  // showPopup(e){
  //   this.setData({
  //      show: true,
  //       placetaped: this.data.place[this.data.active][e.currentTarget.dataset.index]
  //      });
  // console.log(this.data.placetaped)
  // },
  onJump(e){
    if(wx.getStorageSync('user').length==0){
      wx.showModal({
        title: '你还没有登录，请先登录',
        content: '',
        complete: (res) => {
          if (res.cancel) {
            
          }
          if (res.confirm) {
            wx.navigateTo({
              url: '/pages/login/login'
            })
          }
        }
      })
      console.log("1111")
    }else{
      var that = this;
      console.log(e.currentTarget.dataset.index)
      var choose = this.data.choosestamp
      console.log(new Date(choose),1111)
      wx.navigateTo({
        url: `/pages/subscribe/subscribe?id=${e.currentTarget.dataset.index}&lc=${that.data.active.text}&timecode=${that.data.timecode}&choosestamp=${choose}`
      })
    }
  },

  onChange(e){
    var option = e.detail;
    this.setData({
      active: option
    })
    var readingtype_id = option.value
    var that = this
    wx.request({
      url: `http://47.97.167.59:9090/ylrc-reading/list?readingtype_id=${readingtype_id}&subscribe_time=${that.data.choosestamp}&timecode=${that.data.timecode}`,
      method: 'GET',
      header: {
        'content-type': 'application/xml' 
      },
      success: function(res){
        var data = res.data.data

        that.setData({
          place: data,
        })
        console.log(data)
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
    // 加载时间
    var that = this
    var a = Date.parse(new Date());
    var todaystamp = util.formateight(a)
    var today = util.formatDate(new Date());

    var b = Date.parse(new Date())+24*60*60*1000 
    var tomostamp = util.formateight(b)
    var tomo = util.formatDate(new Date(tomostamp))
    var obj = that.data.items
    obj[0].text = today+" 今天"
    obj[1].text = tomo+" 明天"
    that.setData({
      todaystamp: todaystamp,
      tomostamp: tomostamp,
      choosestamp: todaystamp,
      items: obj,
      title: today+'\xa0'+'\xa0'+'上午'
    })


    wx.request({
      url: 'http://47.97.167.59:9090/readingtype/list',
      method: 'GET',
      header: {
        'content-type': 'application/xml'
      },
      success: function(res){
        var data = res.data.data
        var option1 = []
        for(var i = 0;i < data.length;i++){
          option1[i] = {text: data[i].name, value: data[i].id}
        }
        that.setData({
          option1: option1,
          value1: data[0].id,
          active: option1[0]
        })
        wx.request({
          url: `http://47.97.167.59:9090/ylrc-reading/list?readingtype_id=${that.data.value1}&subscribe_time=${that.data.choosestamp}&timecode=${that.data.timecode}`, 
          method: 'GET',
          header: {
            'content-type': 'application/xml' 
          },
          success: function(res){
            var data = res.data.data
            that.setData({
              place: data
            })
            console.log(data)
          },
          fail: function(err){
            console.log(err,'shibai');
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