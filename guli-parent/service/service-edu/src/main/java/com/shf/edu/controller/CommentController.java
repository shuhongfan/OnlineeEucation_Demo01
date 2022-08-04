package com.shf.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.commonutils.JwtUtils;
import com.shf.commonutils.R;
import com.shf.commonutils.ordervo.UcenterMemberOrder;
import com.shf.edu.client.UcenterClient;
import com.shf.edu.entity.Comment;
import com.shf.edu.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //    根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R index(@ApiParam(name = "page", value = "当前页码", required = true)
                   @PathVariable Long page,

                   @ApiParam(name = "limit", value = "每页记录数", required = true)
                   @PathVariable Long limit,

                   @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                   String courseId) {
        Page<Comment> pageParam = new Page<>(page, limit);
        Map<String, Object> map = commentService.getList(pageParam, courseId);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody Comment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);

        UcenterMemberOrder ucenterClientInfo = ucenterClient.getInfo(memberId);

        comment.setNickname(ucenterClientInfo.getNickname());
        comment.setAvatar(ucenterClientInfo.getAvatar());

        commentService.save(comment);

        return R.ok();
    }
}

