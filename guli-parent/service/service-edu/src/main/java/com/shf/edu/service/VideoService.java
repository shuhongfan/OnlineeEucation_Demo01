package com.shf.edu.service;

import com.shf.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.chapter.VideoVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据id查询是否存在视频
     * @param id
     * @return
     */
    boolean getCountByChapterId(String id);

    /**
     * 新增课时
     * @param videoVo
     */
    void saveVideoInfo(VideoVo videoVo);

    /**
     * 根据ID查询课时
     * @param id
     * @return
     */
    VideoVo getVideoInfoById(String id);

    /**
     * 更新课时
     * @param videoVo
     */
    void updateVideoInfo(VideoVo videoVo);

    /**
     * 根据ID删除课时
     * @param id
     * @return
     */
    boolean removeVideoById(String id);
}
