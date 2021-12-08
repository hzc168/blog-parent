package com.hzc.blogapi.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hzc.blogapi.dao.mapper.TagMapper;
import com.hzc.blogapi.dao.pojo.Tag;
import com.hzc.blogapi.service.TagService;
import com.hzc.blogapi.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for(Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tags = tagMapper.findTagsByArticleId(id);
        return copyList(tags);
    }

    @Override
    public List<TagVo> hot(int limit) {
        /**
         * 1. 标签所拥有的文章数量最多 最热标签
         * 2. 查询 根据tag_id 分组 计数，从大到小 排列 取前limit个
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)) {
            return Collections.emptyList();
        }
        // 需要的是 tagId 和 tagName Tag对象
        // select * from tag where id in (1, 2, 3, 4)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return copyList(tagList);
    }

}
