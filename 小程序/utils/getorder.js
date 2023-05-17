wx.chooseMedia({
  count: 1,
  mediaType: ['image'],
  sourceType: ['album'],
  success: function(res) {
    console.log(res.tempFiles.tempFilePath) //本地临时文件路径
  }
})

















