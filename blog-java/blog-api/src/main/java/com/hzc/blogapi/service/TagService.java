package com.hzc.blogapi.service;

import com.hzc.blogapi.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);
}