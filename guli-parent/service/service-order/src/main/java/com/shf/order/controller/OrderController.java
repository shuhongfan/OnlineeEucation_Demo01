package com.shf.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.commonutils.JwtUtils;
import com.shf.commonutils.R;
import com.shf.order.entity.Order;
import com.shf.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("据课程id和用户id创建订单，返回订单id")
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, memberIdByJwtToken);
        return R.ok().data("orderId", orderId);
    }

    @ApiOperation("根据id获取订单信息接口")
    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        Order order = orderService.getByOrderNO(orderId);
        return R.ok().data("item", order);
    }

    @ApiOperation("根据用户id和课程id查询订单信息")
    @GetMapping("isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid,
                               @PathVariable String id) {
        //订单状态是1表示支付成功
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper
                .eq("member_id", memberid)
                .eq("course_id", id)
                .eq("status", 1);
        int count = orderService.count(orderQueryWrapper);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}

