package com.hzc.blogapi.dao.pojo;

import lombok.Data;

@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 內容id
     */
    private Long bodyId;

    /**
     * 置頂
     */
    private int weight = Article_Common;

    /**
     * 创建时间
     */
    private Long createDate;

}