import request from '@/utils/request'
export default {
  getPlayAuth(vid) {
    return request({
      url: `/vod/video/get-play-auth/${vid}`,
      method: 'get'
    })
  }

}
