package com.shf.ucenter.mapper;

import com.shf.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    Integer selectRegisterCount(String day);
}
