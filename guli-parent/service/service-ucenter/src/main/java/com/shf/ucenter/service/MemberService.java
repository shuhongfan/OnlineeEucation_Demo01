package com.shf.ucenter.service;

import com.shf.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ucenter.entity.vo.LoginVo;
import com.shf.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
public interface MemberService extends IService<Member> {

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 会员注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据token获取登录信息
     * @param memberId
     * @return
     */
    Member getLoginInfo(String memberId);

    /**
     * 查询数据库当前用户是否曾经使用过微信登录
     * @param openid
     * @return
     */
    Member getByOpenId(String openid);
}
