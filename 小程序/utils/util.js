const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('/')} ${[hour, minute, second].map(formatNumber).join(':')}`
}

const formatTime1= date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('-')} ${[hour, minute].map(formatNumber).join(':')}`
}

const formatDate = date => {
  const month = date.getMonth()+1
  const day = date.getDate()

  return [month,day].map(formatNumber).join('-')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : `0${n}`
}
function formateight(timestamp){
  var a = new Date(timestamp)
  a.setHours(8)
  a.setMinutes(0)
  a.setSeconds(0)
  var stamp = Date.parse(a)
  return stamp
}
module.exports = {
  formatTime: formatTime,
  formatDate: formatDate,
  formatTime1: formatTime1,
  formateight: formateight
}

