package com.shf.order.service;

import com.shf.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
public interface OrderService extends IService<Order> {

    /**
     * 据课程id和用户id创建订单，返回订单id
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    String saveOrder(String courseId, String memberIdByJwtToken);

    /**
     * 根据id获取订单信息接口
     * @param orderId
     * @return
     */
    Order getByOrderNO(String orderId);
}
