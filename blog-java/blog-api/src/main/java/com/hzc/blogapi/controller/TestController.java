package com.hzc.blogapi.controller;

import com.hzc.blogapi.dao.pojo.SysUser;
import com.hzc.blogapi.utils.UserThreadLocal;
import com.hzc.blogapi.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test() {
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }

}