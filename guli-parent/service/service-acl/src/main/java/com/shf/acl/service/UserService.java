package com.shf.acl.service;

import com.shf.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
public interface UserService extends IService<User> {
    public User selectByUsername(String username);
}
