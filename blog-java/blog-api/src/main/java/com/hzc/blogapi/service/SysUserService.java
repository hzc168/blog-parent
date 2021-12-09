package com.hzc.blogapi.service;

import com.hzc.blogapi.dao.pojo.SysUser;
import com.hzc.blogapi.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long userId);

    SysUser findUser(String account, String pwd);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);
}
