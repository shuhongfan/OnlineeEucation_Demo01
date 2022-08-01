import request from '@/utils/request'

const API_NAME = '/admin/edu/teacher'

export default {
  getPageList(page, limit, searchObj) {
    return request({
      url: `${API_NAME}/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  deleteById(id) {
    return request({
      url: `${API_NAME}/${id}`,
      method: 'delete'
    })
  },
  save(teacher) {
    return request({
      url: `${API_NAME}`,
      method: 'post',
      data: teacher
    })
  },
  getByID(id) {
    return request({
      url: `${API_NAME}/${id}`,
      method: 'get'
    })
  },
  updateById(teacher) {
    return request({
      url: `${API_NAME}/${teacher.id}`,
      method: 'put',
      data: teacher
    })
  },
}
