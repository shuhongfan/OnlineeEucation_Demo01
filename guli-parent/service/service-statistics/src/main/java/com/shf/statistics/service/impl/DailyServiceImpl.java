package com.shf.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.commonutils.R;
import com.shf.statistics.client.UcenterClient;
import com.shf.statistics.entity.Daily;
import com.shf.statistics.mapper.DailyMapper;
import com.shf.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 统计某一天的注册人数
     *
     * @param day
     */
    @Override
    public void createStatisticsByDay(String day) {
//        删除已存在的统计对象
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dailyQueryWrapper);

//        获取统计信息
        Integer registerNum = (Integer) ucenterClient.registerCount(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    /**
     * 统计数据图标展示
     * @param begin
     * @param end
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper
                .select(type, "date_calculated")
                .between("date_calculated", begin, end);
        List<Daily> dailyList = baseMapper.selectList(dailyQueryWrapper);

        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Integer> dataList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        dailyList.forEach(daily -> {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        });
        return map;
    }
}
