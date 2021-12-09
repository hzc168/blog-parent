package com.hzc.blogapi.service;

import com.hzc.blogapi.dao.pojo.SysUser;
import com.hzc.blogapi.vo.Result;
import com.hzc.blogapi.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 注册
     */
    Result register(LoginParam loginParam);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    SysUser checkToken(String token);
}