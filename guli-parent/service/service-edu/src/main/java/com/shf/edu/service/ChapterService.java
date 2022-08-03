package com.shf.edu.service;

import com.shf.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 课程大纲列表
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseID(String courseId);

    /**
     * 根据ID删除章节
     * @param id
     * @return
     */
    boolean remoChapterById(String id);

    /**
     * 根据课程id删除章节
     * @param id
     */
    void remoChapterByCourseId(String id);
}
