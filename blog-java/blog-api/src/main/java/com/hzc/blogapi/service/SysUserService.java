package com.hzc.blogapi.service;

import com.hzc.blogapi.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long userId);
}
