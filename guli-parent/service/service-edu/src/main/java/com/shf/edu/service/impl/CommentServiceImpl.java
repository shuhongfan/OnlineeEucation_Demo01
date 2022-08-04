package com.shf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.edu.entity.Comment;
import com.shf.edu.mapper.CommentMapper;
import com.shf.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    /**
     * 评论分页列表
     * @param pageParam
     * @param courseId
     * @return
     */
    @Override
    public Map<String, Object> getList(Page<Comment> pageParam, String courseId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", courseId);

        baseMapper.selectPage(pageParam, commentQueryWrapper);
        List<Comment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return map;
    }
}
