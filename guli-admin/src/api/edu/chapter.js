import request from '@/utils/request'

const API_NAME = '/admin/edu/chapter'

export default {
  getChapterVideoByCourseID(courseId) {
    return request({
      url: `${API_NAME}/getChapterVideo/${courseId}`,
      method: 'get'
    })
  }
}
