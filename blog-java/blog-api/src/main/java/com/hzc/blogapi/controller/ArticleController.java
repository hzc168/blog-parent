package com.hzc.blogapi.controller;

import com.hzc.blogapi.service.ArticleService;
import com.hzc.blogapi.vo.Result;
import com.hzc.blogapi.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    @PostMapping("new")
    public Result newArticle() {
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

}
