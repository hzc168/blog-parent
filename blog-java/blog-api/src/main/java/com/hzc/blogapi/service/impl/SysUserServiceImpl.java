package com.hzc.blogapi.service.impl;

import com.hzc.blogapi.dao.mapper.SysUserMapper;
import com.hzc.blogapi.dao.pojo.SysUser;
import com.hzc.blogapi.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if(sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("博客系统");
        }
        return sysUser;
    }

}
