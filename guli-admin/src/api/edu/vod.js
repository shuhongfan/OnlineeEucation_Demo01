import request from '@/utils/request'

const API_NAME = '/admin/vod/video'

export default {

  removeById(id) {
    return request({
      url: `${API_NAME}/${id}`,
      method: 'delete'
    })
  }
}
