package com.shf.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.commonutils.ordervo.CourseWebVoOrder;
import com.shf.commonutils.ordervo.UcenterMemberOrder;
import com.shf.order.client.EduClient;
import com.shf.order.client.UcenterClient;
import com.shf.order.entity.Order;
import com.shf.order.mapper.OrderMapper;
import com.shf.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.order.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 据课程id和用户id创建订单，返回订单id
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    @Override
    public String saveOrder(String courseId, String memberId) {
//        远程调用课程服务，根据课程id获取课程信息
        CourseWebVoOrder courseDto = eduClient.getCourseInfoDto(courseId);

        UcenterMemberOrder ucenterMember = ucenterClient.getInfo(memberId);

//        创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }

    /**
     * 根据id获取订单信息接口
     * @param orderId
     * @return
     */
    @Override
    public Order getByOrderNO(String orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no", orderId);
        Order order = baseMapper.selectOne(orderQueryWrapper);
        return order;
    }
}
