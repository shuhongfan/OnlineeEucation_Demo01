package com.shf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.commonutils.R;
import com.shf.edu.client.VodClient;
import com.shf.edu.entity.Video;
import com.shf.edu.entity.chapter.VideoVo;
import com.shf.edu.mapper.VideoMapper;
import com.shf.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private VodClient vodClient;

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
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeVideoById(String id) {
//        查询云端视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();

//        删除视频资源
        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = vodClient.removeVideo(videoSourceId);
            if (r.getCode()==20001) {
                throw new GuliException(20001, "熔断");
            }
        }


        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 根据课程id删除小节
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeByCourseId(String id) {
//        根据课程ID查询所有视频列表
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id)
                .select("video_source_id", id);
        List<Video> videoList = baseMapper.selectList(videoQueryWrapper);

//        得到所有视频列表的云端原始视频
//        List<String> videoSourceIdList = new ArrayList<>();
//        videoList.forEach(video -> {
//            String videoSourceId = video.getVideoSourceId();
//            if (!StringUtils.isEmpty(videoSourceId)) {
//                videoSourceIdList.add(videoSourceId);
//            }
//        });
        List<String> videoSourceIdList = videoList
                .stream()
                .map(Video::getVideoSourceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

//        调用vod服务远程删除视频
        if (videoSourceIdList.size() > 0) {
            vodClient.removeVideoList(videoSourceIdList);
        }

//        删除video表示的记录
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        int count = baseMapper.delete(wrapper);
        return count > 0;
    }
}
