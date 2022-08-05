package com.shf.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.commonutils.JwtUtils;
import com.shf.commonutils.MD5;
import com.shf.servicebase.exception.GuliException;
import com.shf.ucenter.entity.Member;
import com.shf.ucenter.entity.vo.LoginVo;
import com.shf.ucenter.entity.vo.RegisterVo;
import com.shf.ucenter.mapper.MemberMapper;
import com.shf.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

//        校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)) {
            throw new GuliException(20001, "error");
        }

//        获取会员
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile", mobile);
        Member member = baseMapper.selectOne(memberQueryWrapper);
        if (member == null) {
            throw new GuliException(20001, "error");
        }

//        校验密码
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001, "error");
        }

//        校验是否被禁用
        if (member.getIsDisabled()) {
            throw new GuliException(20001, "error");
        }

//        使用JWT生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    /**
     * 会员注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
//        获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

//        校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "error");
        }

//        校验验证码
//        从redis获取发送的验证码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)) {
            throw new GuliException(20001, "error");
        }

//        查询数据库中是否存在相同的手机号码
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(memberQueryWrapper);
        if (count.intValue() > 0) {
            throw new GuliException(20001, "error");
        }

        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://srb-shf.oss-cn-hangzhou.aliyuncs.com/avatar/default.jpg");
        this.save(member);
    }

    /**
     * 根据token获取登录信息
     * @param memberId
     * @return
     */
    @Override
    public Member getLoginInfo(String memberId) {
        Member member = baseMapper.selectById(memberId);
        member.setPassword("");
        return member;
    }

    /**
     * 查询数据库当前用户是否曾经使用过微信登录
     * @param openid
     * @return
     */
    @Override
    public Member getByOpenId(String openid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid", openid);
        return baseMapper.selectOne(memberQueryWrapper);
    }

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }

}
