package com.shf.statistics.service;

import com.shf.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
public interface DailyService extends IService<Daily> {

    /**
     * 统计某一天的注册人数
     * @param day
     */
    void createStatisticsByDay(String day);

    /**
     * 统计数据图标展示
     * @param begin
     * @param end
     * @param type
     * @return
     */
    Map<String, Object> getChartData(String begin, String end, String type);
}
