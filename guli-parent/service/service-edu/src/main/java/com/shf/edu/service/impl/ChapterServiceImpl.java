package com.shf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.edu.client.VodClient;
import com.shf.edu.entity.Chapter;
import com.shf.edu.entity.Video;
import com.shf.edu.entity.chapter.ChapterVo;
import com.shf.edu.entity.chapter.VideoVo;
import com.shf.edu.mapper.ChapterMapper;
import com.shf.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.edu.service.VideoService;
import com.shf.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 课程大纲列表
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseID(String courseId) {
        List<ChapterVo> chapterVoList = new ArrayList<ChapterVo>();

//        1. 根据课程id查询课程里面所有的章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

//        2. 根据课程id查询课程里面所有的小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<Video> videoList = videoService.list(wrapper);

//        3.遍历查询章节list集合进行封装
        chapterList.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoList.add(chapterVo);
//        4.遍历查询小节list集合，进行封装
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            videoList.forEach(video -> {
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            });
            chapterVo.setChildren(videoVoArrayList);
        });

        return chapterVoList;
    }

    /**
     * 根据ID删除章节
     * @param id
     * @return
     */
    @Override
    public boolean remoChapterById(String id) {
//        根据id查询是否存在视频，如果有自提示用户尚有子节点
        if (videoService.getCountByChapterId(id)) {
            throw new GuliException(20001, "该章节下存在视频课程，请先删除视频课程");
        }

        int res = baseMapper.deleteById(id);
        return res > 0;
    }

    /**
     * 根据课程id删除章节
     * @param id
     */
    @Override
    public void remoChapterByCourseId(String id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }

}
