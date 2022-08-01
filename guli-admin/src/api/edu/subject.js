import request from '@/utils/request'

const API_NAME = '/admin/edu/subject'

export default {
  getNestedTreeList() {
    return request({
      url: `${API_NAME}`,
      method: 'get'
    })
  }
}
