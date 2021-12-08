package com.hzc.blogapi.service.impl;

import com.hzc.blogapi.dao.mapper.TagMapper;
import com.hzc.blogapi.dao.pojo.Tag;
import com.hzc.blogapi.service.TagService;
import com.hzc.blogapi.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

}