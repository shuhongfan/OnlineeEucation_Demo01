import request from '@/utils/request'

const API_NAME = '/statistics/daily'
export default {

  createStatistics(day) {
    return request({
      url: `${API_NAME}/${day}`,
      method: 'post'
    })
  },
  showChart(searchObj) {
    return request({
      url: `${API_NAME}/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
      method: 'get'
    })
  }
}
