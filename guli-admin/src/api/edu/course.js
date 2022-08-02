import request from '@/utils/request'

const API_NAME = '/admin/edu/course'

export default {
  saveCourseInfo(courseInfoVo) {
    return request({
      url: `${API_NAME}/save-course-info`,
      method: 'post',
      data: courseInfoVo
    })
  },
  getListTeacher() {
    return request({
      url: `/admin/edu/teacher`,
      method: 'get'
    })
  },
  getCourseInfoFormById(id) {
    return request({
      url: `${API_NAME}/course-info/${id}`,
      method: 'get'
    })
  },
  updateCourseInfoById(courseInfoVo) {
    return request({
      url: `${API_NAME}/update-course-info`,
      method: 'put',
      data: courseInfoVo
    })
  },
}
