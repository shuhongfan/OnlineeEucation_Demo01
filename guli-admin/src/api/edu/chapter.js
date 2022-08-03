import request from '@/utils/request'

export default {
  //1 根据课程id获取章节和小节数据列表
  getAllChapterVideo(courseId) {
    return request({
      url: '/admin/edu/chapter/getChapterVideo/' + courseId,
      method: 'get'
    })
  },
  //添加章节
  addChapter(chapter) {
    return request({
      url: '/admin/edu/chapter',
      method: 'post',
      data: chapter
    })
  },
  //根据id查询章节
  getChapter(chapterId) {
    return request({
      url: '/admin/edu/chapter/' + chapterId,
      method: 'get'
    })
  },
  //修改章节
  updateChapter(chapter) {
    return request({
      url: '/admin/edu/chapter',
      method: 'put',
      data: chapter
    })
  },
  //删除章节
  deleteChapter(chapterId) {
    return request({
      url: '/admin/edu/chapter/' + chapterId,
      method: 'delete'
    })
  },
}
