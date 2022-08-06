import request from '@/utils/request'

export default {
  //条件分页课程查询的方法
  getCourseList(page,limit,searchObj) {
    return request({
      url: `/edu/api/course/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/edu/admin/subject',
      method: 'get'
    })
  },
  //课程详情的方法
  getCourseInfo(id) {
    return request({
      url: '/edu/api/course/'+id,
      method: 'get'
    })
  }

}
