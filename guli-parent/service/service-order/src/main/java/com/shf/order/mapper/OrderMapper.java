package com.shf.order.mapper;

import com.shf.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
