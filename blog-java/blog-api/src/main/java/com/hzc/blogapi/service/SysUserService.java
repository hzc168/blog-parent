package com.hzc.blogapi.service;

import com.hzc.blogapi.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long userId);

    SysUser findUser(String account, String pwd);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);
}
