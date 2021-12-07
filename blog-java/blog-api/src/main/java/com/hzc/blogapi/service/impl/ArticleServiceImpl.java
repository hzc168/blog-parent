package com.hzc.blogapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzc.blogapi.dao.mapper.ArticleMapper;
import com.hzc.blogapi.dao.pojo.Article;
import com.hzc.blogapi.service.ArticleService;
import com.hzc.blogapi.vo.ArticleVo;
import com.hzc.blogapi.vo.Result;
import com.hzc.blogapi.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    // 注入对数据层对访问
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1. 分页查询 article 数据库表
         * 2. 定义查询条件
         * 3. 是否需要排序
         * 4. 查询
         * 5. 对数据库返回数据进行处理
         * 6. 返回结果
         */
        // 分页查询
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 排序 时间排序 是否置顶排序(weight)
        // order by create_date desc
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        // 传入查询条件 进行查询
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        // 目前还不能直接返回，因为此时对records数据存在许多类型不同，需要通过vo层对数据处理和前端交互
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();

        for(Article record : records) {
            articleVoList.add(copy(record));
        }

        return articleVoList;
    }

    // eop的作用是对应copyList，集合之间的copy分解成集合元素之间的copy
    private ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }

}




































