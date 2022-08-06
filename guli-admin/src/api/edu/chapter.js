import request from '@/utils/request'

export default {
  //1 根据课程id获取章节和小节数据列表
  getAllChapterVideo(courseId) {
    return request({
      url: '/edu/admin/chapter/getChapterVideo/' + courseId,
      method: 'get'
    })
  },
  //添加章节
  addChapter(chapter) {
    return request({
      url: '/edu/admin/chapter',
      method: 'post',
      data: chapter
    })
  },
  //根据id查询章节
  getChapter(chapterId) {
    return request({
      url: '/edu/admin/chapter/' + chapterId,
      method: 'get'
    })
  },
  //修改章节
  updateChapter(chapter) {
    return request({
      url: '/edu/admin/chapter',
      method: 'put',
      data: chapter
    })
  },
  //删除章节
  deleteChapter(chapterId) {
    return request({
      url: '/edu/admin/chapter/' + chapterId,
      method: 'delete'
    })
  },
}
