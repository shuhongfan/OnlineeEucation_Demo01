package com.shf.ucenter.controller;

import com.google.gson.Gson;
import com.netflix.ribbon.proxy.annotation.Http;
import com.shf.commonutils.JwtUtils;
import com.shf.servicebase.exception.GuliException;
import com.shf.ucenter.entity.Member;
import com.shf.ucenter.service.MemberService;
import com.shf.ucenter.util.ConstantPropertiesUtil;
import com.shf.ucenter.util.HttpClientUtils;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;


@Controller
@RequestMapping("/ucenter/api/wx")
public class WxApiController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MemberService memberService;

    @ApiOperation("登录")
    @GetMapping("login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

//        回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        redisTemplate.opsForValue().set("state", state);

        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state
        );
        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问
        String redisState = redisTemplate.opsForValue().get("state");
        if (!redisState.equals(state)) {
            throw new GuliException(20001, "非法访问");
        }

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken========" + accessTokenUrl);
        } catch (Exception e) {
            throw new GuliException(20001, "获取Access——token失败");
        }

//        解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");

//        查询数据库当前用户是否曾经使用过微信登录
        Member member = memberService.getByOpenId(openid);
        if (member == null) {
            System.out.println("新用户注册");

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);

            String userInfo = null;
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userINfo：" + userInfo);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

//            解析
            HashMap<String,Object> userInfoHashMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoHashMap.get("nickname");
            String headimgurl = (String) userInfoHashMap.get("headimgurl");

//            向数据库插入一条 记录
            member = new Member();
            member.setNickname(nickname)
                    .setOpenid(openid)
                    .setAvatar(headimgurl);
            memberService.save(member);
        }

//        TODO 登录
//        生成JWT
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}
