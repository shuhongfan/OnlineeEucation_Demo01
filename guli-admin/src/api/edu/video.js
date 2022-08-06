import request from '@/utils/request'

export default {

  //添加小节
  addVideo(video) {
    return request({
      url: '/edu/admin/video/save-video-info',
      method: 'post',
      data: video
    })
  },

  //删除小节
  deleteVideo(id) {
    return request({
      url: '/edu/admin/video/' + id,
      method: 'delete'
    })
  },
  //删除视频
  deleteAliyunvod(id) {
    return request({
      url: '/vod/admin/video/' + id,
      method: 'delete'
    })
  }

}
