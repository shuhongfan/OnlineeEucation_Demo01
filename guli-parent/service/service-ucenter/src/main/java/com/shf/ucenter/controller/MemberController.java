package com.shf.ucenter.controller;


import com.shf.commonutils.JwtUtils;
import com.shf.commonutils.R;
import com.shf.commonutils.ordervo.UcenterMemberOrder;
import com.shf.ucenter.entity.Member;
import com.shf.ucenter.entity.vo.LoginVo;
import com.shf.ucenter.entity.vo.RegisterVo;
import com.shf.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation("会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getLoginInfo(memberId);
        return R.ok().data("item", member);
    }

    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable String id) {
//        根据用户id获取用户信息
        Member member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @GetMapping(value = "countregister/{day}")
    public R registerCount(@PathVariable String day){
        Integer countRegister = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", countRegister);
    }
}

