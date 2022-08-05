package com.shf.order.controller;


import com.shf.commonutils.R;
import com.shf.order.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/order/log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation("生成二维码")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map<String,Object> map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation("获取支付状态接口")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
//            更新订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().data("success",true).message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

