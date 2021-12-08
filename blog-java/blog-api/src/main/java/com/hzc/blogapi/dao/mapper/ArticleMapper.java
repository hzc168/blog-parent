package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.Article;
import com.hzc.blogapi.dao.dos.Archives;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

}