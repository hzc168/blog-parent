package com.hzc.blogapi.service;

import com.hzc.blogapi.vo.Result;
import com.hzc.blogapi.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */

    Result listArticle(PageParams pageParams);
}
