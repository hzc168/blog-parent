package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签 前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}