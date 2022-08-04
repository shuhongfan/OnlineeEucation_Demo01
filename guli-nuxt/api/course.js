import request from '@/utils/request'

export default {
  //条件分页课程查询的方法
  getCourseList(page,limit,searchObj) {
    return request({
      url: `/api/edu/course/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/admin/edu/subject',
      method: 'get'
    })
  },
  //课程详情的方法
  getCourseInfo(id) {
    return request({
      url: '/api/edu/course/'+id,
      method: 'get'
    })
  }

}
