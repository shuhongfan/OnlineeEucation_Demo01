import request from '@/utils/request'

export default {
  //1 添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: '/edu/admin/course/save-course-info',
      method: 'post',
      data: courseInfo
    })
  },
  //2 查询所有讲师
  getListTeacher() {
    return request({
      url: '/edu/admin/teacher',
      method: 'get'
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfoId(id) {
    return request({
      url: '/edu/admin/course/course-info/' + id,
      method: 'get'
    })
  },
  //修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: '/edu/admin/course/update-course-info',
      method: 'put',
      data: courseInfo
    })
  },
  //课程确认信息显示
  getPublihCourseInfo(id) {
    return request({
      url: '/edu/admin/course/course-publish-info/' + id,
      method: 'get'
    })
  },
  //课程最终发布
  publihCourse(id) {
    return request({
      url: '/edu/admin/course/publish-course/' + id,
      method: 'post'
    })
  },
  //TODO 课程列表
  //课程最终发布
  getListCourse(page, limit, obj) {
    return request({
      url: `/edu/admin/course/${page}/${limit}`,
      method: 'get',
      params: obj
    })
  }

}
