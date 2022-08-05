package com.shf.order.service;

import com.shf.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成二维码
     * @param orderNo
     * @return
     */
    Map<String, Object> createNative(String orderNo);

    /**
     * 获取支付状态接口
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
