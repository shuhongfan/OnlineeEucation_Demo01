package com.shf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.edu.entity.Video;
import com.shf.edu.entity.chapter.VideoVo;
import com.shf.edu.mapper.VideoMapper;
import com.shf.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /**
     * 根据id查询是否存在视频
     *
     * @param id
     * @return
     */
    @Override
    public boolean getCountByChapterId(String id) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        Integer count = baseMapper.selectCount(videoQueryWrapper);
        return count > 0;
    }

    /**
     * 新增课时
     *
     * @param videoVo
     */
    @Override
    public void saveVideoInfo(VideoVo videoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoVo, video);
        boolean result = this.save(video);
        if (!result) {
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    /**
     * 根据ID查询课时
     *
     * @param id
     * @return
     */
    @Override
    public VideoVo getVideoInfoById(String id) {
        Video video = this.getById(id);
        if (video == null) {
            throw new GuliException(20001, "数据不存在");
        }
        VideoVo videoVo = new VideoVo();
        BeanUtils.copyProperties(video, videoVo);
        return videoVo;
    }

    /**
     * 更新课时
     *
     * @param videoVo
     */
    @Override
    public void updateVideoInfo(VideoVo videoVo) {
//        保存课时基本信息
        Video video = new Video();
        BeanUtils.copyProperties(videoVo, video);
        boolean result = this.updateById(video);
        if (!result) {
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    /**
     * 根据ID删除课时
     * @param id
     * @return
     */
    @Override
    public boolean removeVideoById(String id) {


        int result = baseMapper.deleteById(id);
        return result>0;
    }
}
