package com.shf.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
public interface CommentService extends IService<Comment> {

    /**
     * 评论分页列表
     * @param pageParam
     * @param courseId
     * @return
     */
    Map<String, Object> getList(Page<Comment> pageParam, String courseId);
}
