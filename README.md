# springboot 练手项目

基于注解开发文档：https://segmentfault.com/a/1190000025187511

解决mapper爆红：https://blog.csdn.net/hanqing456/article/details/111878930?utm_medium=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~default-1.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~default-1.no_search_link

分页查询：https://blog.csdn.net/weixin_41010294/article/details/105726879

BeanUtils.copyProperties用法   https://blog.csdn.net/Mr_linjw/article/details/50236279





说明：

1. 提供前端工程，只需要实现后端接口即可
2. 项目以单体架构入手，先快速开发，不考虑项目优化
3. 开发完成后，开始优化项目，提升编程思维能力
4. 比如页面静态化，缓存，云存储，日志等
5. docker部署上线
6. 云服务器购买，域名购买，域名备案等

项目使用技术 ：

springboot + mybatisplus+redis+mysql


### 1. 工程搭建

#### 1.1 新建maven工程

新建`blog-java`作为父项目，新建module`blog-api`作为子模块，目录结构如下图：

图片

父`pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.1</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.hzc</groupId>
    <artifactId>blog-java</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>blog-java</name>
    <description>Demo project for Spring Boot</description>
    <modules>
        <module>blog-api</module>
    </modules>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencyManagement>
        <dependencies>

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>1.2.76</version>
            </dependency>

            <dependency>
                <groupId>commons-collections</groupId>
                <artifactId>commons-collections</artifactId>
                <version>3.2.2</version>
            </dependency>

            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>3.4.3</version>
            </dependency>
            <!-- https://mvnrepository.com/artifact/joda-time/joda-time -->
            <dependency>
                <groupId>joda-time</groupId>
                <artifactId>joda-time</artifactId>
                <version>2.10.10</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

子模块`pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>blog-java</artifactId>
        <groupId>com.hzc</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>blog-api</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <!-- 排除 默认使用的logback  -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- log4j2 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>


        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.76</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.2</version>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.3</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.10.10</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>

        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>[7.7.0, 7.7.99]</version>
        </dependency>
    </dependencies>
</project>
```

### 2 配置

#### 2.1 配置文件

`application.properties`中配置端口、数据库、mubitsPlus基本配置等：

```properties
# server
server.port=8088
spring.application.name=blog-java
# datasource
spring.datasource.url=jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC
spring.datasource.username=root
spring.datasource.password=12345678
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#mybatis-plus
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
mybatis-plus.global-config.db-config.table-prefix=ms_
```

#### 2.2 分页插件

新建mybatis配置文件，并配置分页插件

`com.hzc.blogapi.config.MybatisPlusConfig`

```java
package com.hzc.blogapi.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.hzc.blogapi.dao")     // 扫包，将此包下的接口生成代理实现类，并且注册到spring容器中
public class MybatisPlusConfig {
    // 分頁插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
```

#### 2.3 设置跨域

`com.hzc.blogapi.config.WebConfig`

```java
package com.hzc.blogapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名端口不一致
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}
```

#### 2.4 启动类

`com.hzc.blogapi.BlogApiApplication`

```java
package com.hzc.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

}
```

### 3. 首页-文章列表

#### 3.1 接口说明

##### 接口描述

##### 请求

- **请求语法**

  ```http
  POST /articles HTTP/1.1
  ```

- **请求参数**

  > 无

- **请求内容**

  ```json
  {
      page,
      pageSize
  }
  ```
  
- **请求内容参数**

  > pageSize：类型int
  >
  > 每页显示的数量 page：类型int， 当前页数

##### 响应

- **响应内容**

  ```json
  {
      "success": true,
      "code": 200,
      "msg": "success",
      "data": [
          {
              "id": 1,
              "title": "springboot介绍以及入门案例",
              "summary": "通过Spring Boot实现的服务，只需要依靠一个Java类，把它打包成jar，并通过`java -jar`命令就可以运行起来。\r\n\r\n这一切相较于传统Spring应用来说，已经变得非常的轻便、简单。",
              "commentCounts": 2,
              "viewCounts": 54,
              "weight": 1,
              "createDate": "2609-06-26 15:58",
              "author": "12",
              "body": null,
              "tags": [
                  {
                      "id": 5,
                      "avatar": null,
                      "tagName": "444"
                  }
              ],
              "categorys": null
          }
      ]
  }
  ```
  
- **响应内容参数**

  > code：类型`int`，状态码，200表示成功；

##### 示例

- **请求示例**

  ```http
  POST /articles HTTP/1.1
  ```

  ```json
  {
      page: 1,
      pageSize: 10
  }
  ```

  

- **响应示例**

  ```json
  {
      "success": true,
      "code": 200,
      "msg": "success",
      "data": [
          {
              "id": 1,
              "title": "springboot介绍以及入门案例",
              "summary": "通过Spring Boot实现的服务，只需要依靠一个Java类，把它打包成jar，并通过`java -jar`命令就可以运行起来。\r\n\r\n这一切相较于传统Spring应用来说，已经变得非常的轻便、简单。",
              "commentCounts": 2,
              "viewCounts": 54,
              "weight": 1,
              "createDate": "2609-06-26 15:58",
              "author": "12",
              "body": null,
              "tags": [
                  {
                      "id": 5,
                      "avatar": null,
                      "tagName": "444"
                  },
                  {
                      "id": 7,
                      "avatar": null,
                      "tagName": "22"
                  },
                  {
                      "id": 8,
                      "avatar": null,
                      "tagName": "11"
                  }
              ],
              "categorys": null
          },
          {
              "id": 9,
              "title": "Vue.js 是什么",
              "summary": "Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。",
              "commentCounts": 0,
              "viewCounts": 3,
              "weight": 0,
              "createDate": "2609-06-27 11:25",
              "author": "12",
              "body": null,
              "tags": [
                  {
                      "id": 7,
                      "avatar": null,
                      "tagName": "22"
                  }
              ],
              "categorys": null
          },
          {
              "id": 10,
              "title": "Element相关",
              "summary": "本节将介绍如何在项目中使用 Element。",
              "commentCounts": 0,
              "viewCounts": 3,
              "weight": 0,
              "createDate": "2609-06-27 11:25",
              "author": "12",
              "body": null,
              "tags": [
                  {
                      "id": 5,
                      "avatar": null,
                      "tagName": "444"
                  },
                  {
                      "id": 6,
                      "avatar": null,
                      "tagName": "33"
                  },
                  {
                      "id": 7,
                      "avatar": null,
                      "tagName": "22"
                  },
                  {
                      "id": 8,
                      "avatar": null,
                      "tagName": "11"
                  }
              ],
              "categorys": null
          }
      ]
  }
  ```

  

#### 3.2 编码

##### 3.2.1 创建表

```sql
CREATE TABLE `blog`.`ms_article`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `comment_counts` int(0) NULL DEFAULT NULL COMMENT '评论数量',
  `create_date` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `view_counts` int(0) NULL DEFAULT NULL COMMENT '浏览数量',
  `weight` int(0) NOT NULL COMMENT '是否置顶',
  `author_id` bigint(0) NULL DEFAULT NULL COMMENT '作者id',
  `body_id` bigint(0) NULL DEFAULT NULL COMMENT '内容id',
  `category_id` int(0) NULL DEFAULT NULL COMMENT '类别id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

```sql
CREATE TABLE `blog`.`ms_tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(0) NOT NULL,
  `tag_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  INDEX `tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

```sql
CREATE TABLE `blog`.`ms_sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `admin` bit(1) NULL DEFAULT NULL COMMENT '是否管理员',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_date` bigint(0) NULL DEFAULT NULL COMMENT '注册时间',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '是否删除',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `last_login` bigint(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `mobile_phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

##### 3.2.2 新建实体类

entity层，别名：model层，domain层

用途：实体层，用于存放我们的实体类，与数据库中的属性值基本保持一致，实现set和get的方法。

**创建文章类**

`com.hzc.blogapi.dao.pojo`

```java
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
```

**创建用户类**

`com.hzc.blogapi.dao.pojo.SysUser`

```java
package com.hzc.blogapi.dao.pojo;

import lombok.Data;

@Data
public class SysUser {

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;

}
```

**创建标签类**

`com.hzc.blogapi.dao.pojo.Tag`

```java
package com.hzc.blogapi.dao.pojo;

import lombok.Data;

@Data
public class Tag {

    private Long id;

    private String avatar;

    private String tagName;

}
```



#### 接口实现思路

##### 1. 新建负责业务调度的controller，

`com.hzc.blogapi.controller.ArticleController`

```java
@RestController
@RequestMapping("articles")
public class ArticleController {

    @PostMapping
    public void listArticle() {

    }

}
```



##### 2. 接口接收两个参数，page，pageSize。定义一个参数类

`com.hzc.blogapi.vo.params.PageParams`

```java
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

}
```

##### 3. 我们接口返回的类型基本一致，所以我们可以建立一个统一结果类。

`com.hzc.blogapi.vo.Result`

```java
package com.hzc.blogapi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(false, code, msg, null);
    }
}
```

##### 4. 此时，我们应当返回接口请求数据，我们的ArticleController应当如此：

```java
@RestController
@RequestMapping("articles")
public class ArticleController {

    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return Result.success(结果数据);
    }

}
```

##### 5. 但是，我们该如何得到结果数据，以及如何处理业务逻辑。

此时，我们可以添加service层。

```java
@RestController
@RequestMapping("articles")
public class ArticleController {

    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

}
```

新建`ArticleService`接口，`com.hzc.blogapi.service.ArticleService`

```java
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
```

文章实现类 `com.hzc.blogapi.service.impl.ArticleServiceImpl`

```java

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

```

##### 6. 补充mapper层，`com.hzc.blogapi.dao.mapper.ArticleMapper`

```java
package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.Article;

public interface ArticleMapper extends BaseMapper<Article> {
}
```



##### 7. 补充 `ArticleVo`，`com.hzc.blogapi.vo.ArticleVo`

```java
package com.hzc.blogapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

//    private ArticleBodyVo body;

    private List<TagVo> tags;

//    private CategoryVo category;
}
```



此时articles可以正常的出来了。

#### 文章列表添加作者和标签

##### 3.2.3 Controller

控制器，导入service层。

Controller是负责业务调度的，所以在这一层写一些业务的调度代码，而具体的业务处理应放在service中去写，而且service不单纯是对于dao的增删改查的调用，service是业务层，所以应该更切近与具体业务功能要求，所以在这一层，一个方法锁体现的是一个可以对外提供的功能，比如购物商城中的生成订单方法，这里面就不简单是增加个订单记录那么简单，我们需要查询库存，核对商品等一系列实际业务逻辑的处理；

**文章Controller**

`com.hzc.blogapi.controller.ArticleController`

```java
package com.hzc.blogapi.controller;

import com.hzc.blogapi.service.ArticleService;
import com.hzc.blogapi.vo.ArticleVo;
import com.hzc.blogapi.vo.Result;
import com.hzc.blogapi.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    // Result是统一结果返回
    @PostMapping
    public Result articles(@RequestBody PageParams pageParams) {
        // ArticleVo 页面接收的数据
        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);

        return Result.success(articles);
    }

}
```



##### 3.2.5 建立与前端交互的Vo文件

**文章vo**

`com.hzc.blogapi.vo.ArticleVo`

```java
package com.hzc.blogapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

//    private ArticleBodyVo body;

    private List<TagVo> tags;

//    private CategoryVo category;
}
```

**用户vo**

`com.hzc.blogapi.vo.SysUserVo`

```java
package com.hzc.blogapi.vo;

import lombok.Data;

@Data
public class SysUserVo {
    private String nickname;

    private String avatar;

    private String id;
}

```

**标签vo**

`com.hzc.blogapi.vo`

```java
package com.hzc.blogapi.vo;

import lombok.Data;

@Data
public class TagVo {

    private String id;

    private String tagName;

    private String avatar;
}
```

##### 3.2.6 Service

service层主要是写业务逻辑方法，service层经常要调用dao层（也叫mapper层）的方法对数据进行增删改查的操作。

**文章Service**

`com.hzc.blogapi.service.ArticleService`

```java
package com.hzc.blogapi.service;

import com.hzc.blogapi.vo.ArticleVo;
import com.hzc.blogapi.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    
    List<ArticleVo> listArticle(PageParams pageParams);

}
```

`com.hzc.blogapi.service.impl.ArticleServiceImpl`

```java
package com.hzc.blogapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzc.blogapi.dao.mapper.ArticleMapper;
import com.hzc.blogapi.dao.pojo.Article;
import com.hzc.blogapi.service.ArticleService;
import com.hzc.blogapi.service.SysUserService;
import com.hzc.blogapi.service.TagService;
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
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TagService tagService;

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
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();

        for(Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor));
        }

        return articleVoList;
    }

    // eop的作用是对应copyList，集合之间的copy分解成集合元素之间的copy
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 并不是所有的接口都需要标签和作者信息
        if(isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor) {
            // 拿到作者id
            Long authorId = article.getAuthorId();

            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }

}
```

**用户service**

`com.hzc.blogapi.service.SysUserService`

```java
package com.hzc.blogapi.service;

import com.hzc.blogapi.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long userId);
}
```

实现类：

`com.hzc.blogapi.service.impl.SysUserServiceImpl`

```java
package com.hzc.blogapi.service.impl;

import com.hzc.blogapi.dao.mapper.SysUserMapper;
import com.hzc.blogapi.dao.pojo.SysUser;
import com.hzc.blogapi.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if(sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("博客系统");
        }
        return sysUser;
    }

}
```

**标签service**

`com.hzc.blogapi.service.TagService`

```java
package com.hzc.blogapi.service;

import com.hzc.blogapi.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);
}
```

实现类：

`com.hzc.blogapi.service.impl.TagsServiceImpl`

```java
package com.hzc.blogapi.service.impl;

import com.hzc.blogapi.dao.mapper.TagMapper;
import com.hzc.blogapi.dao.pojo.Tag;
import com.hzc.blogapi.service.TagService;
import com.hzc.blogapi.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagService {

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
```


##### 3.2.7 Mapper

mapper层及dao层，主要是做数据持久层的工作，负责与数据库进行联络的一些任务都封装在此。service层经常要调用dao层（mapper层）的方法对数据进行增删改查的操作。

现在用mybatis逆向工程生成的mapper层，其实就是dao层。

dao层对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的，而service层是针对我们controller，也就是针对我们使用者。service的impl是把mapper和service进行整合的文件。

dao层和service层关系：service层经常要调用dao层的方法对数据进行增删改查的操作，现实开发中，对业务的操作会涉及到数据的操作，而对数据操作常常要用到数据库，所以service层会场景调用dao层的方法。

**文章Mapper**

`com.hzc.blogapi.dao.mapper.ArticleMapper`

```java
package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.Article;

public interface ArticleMapper extends BaseMapper<Article> {
}
```

**用户Mapper**

`com.hzc.blogapi.dao.mapper.SysUserMapper`

```java
package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
}
```

**标签Mapper**

`com.hzc.blogapi.dao.mapper.TagMapper`

```java
package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long articleId);
}
```

##### MyBatis配置文件

`com.hzc.blogapi.dao.mapper.TagMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--MyBatis配置文件-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.hzc.blogapi.dao.mapper.TagMapper">

    <sql id="all">
        id,avatar,tag_name as tagName
    </sql>

    <select id="findTagsByArticleId" parameterType="long" resultType="com.hzc.blogapi.dao.pojo.Tag">
        select <include refid="all" />  from ms_tag
        <where>
            id in
            (select tag_id from ms_article_tag where article_id = #{articleId})
        </where>
    </select>
</mapper>
```


### 4. 首页-最热标签

#### 4.1 标签接口说明

##### 接口描述

##### 请求

- **请求语法**

  ```http
  GET /tags/hot HTTP/1.1
  ```

- **请求参数**

  > 无

- **请求内容**

  > 无

- **请求内容参数**

  > 无

##### 响应

- **响应内容**

  ```json
  {
      "success": true,
      "code": 200,
      "msg": "success",
      "data": [
          {
              "id":1,
              "tagName":"4444"
          }
      ]
  }
  ```

- **响应内容参数**

  > code：类型`int`，状态码，200表示成功；

##### 示例

- **请求示例**

  ```http
  POST /tags/hot HTTP/1.1
  ```

- **响应示例**

  ```json
  {
      "success": true,
      "code": 200,
      "msg": "success",
      "data": [
          {
              "id":1,
              "tagName":"4444"
          }
      ]
  }
  ```


#### 4.2 编码

##### 4.2.1 Controller

`com.hzc.blogapi.controller.TagController`

```java
package com.hzc.blogapi.controller;

import com.hzc.blogapi.service.TagService;
import com.hzc.blogapi.vo.Result;
import com.hzc.blogapi.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> tagVoList = tagService.hot(limit);
        return Result.success(tagVoList);
    }
}
```

`com.hzc.blogapi.vo.TagVo`

```java
package com.hzc.blogapi.vo;

import lombok.Data;

@Data
public class TagVo {

    private String id;

    private String tagName;

    private String avatar;
}
```

##### 4.2.2 Service

`com.hzc.blogapi.service.impl.TagsServiceImpl`

```java
package com.hzc.blogapi.service.impl;

import com.hzc.blogapi.dao.mapper.TagMapper;
import com.hzc.blogapi.dao.pojo.Tag;
import com.hzc.blogapi.service.TagService;
import com.hzc.blogapi.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagService {

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
```

`com.hzc.blogapi.service.TagService`补充：

```java
List<TagVo> hot(int limit);
```

##### 4.2.3 Mapper

```java
package com.hzc.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzc.blogapi.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByTagIds(List<Long> tagIds);

    /**
     * 查询最热的标签 前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
```

##### Mapper配置文件

`resources.com.hzc.blogapi.dao.mapper.TagMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--MyBatis配置文件-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.hzc.blogapi.dao.TagMapper">

    <sql id="all">
        id,avatar,tag_name as tagName
    </sql>

    <select id="findTagsByArticleId" parameterType="long" resultType="com.hzc.blogapi.dao.pojo.Tag">
        select <include refid="all" />  from ms_tag
        <where>
            id in
            (select tag_id from ms_article_tag where article_id = #{articleId})
        </where>
    </select>
  
  <!-- List<Long> findHotsTagIds(int limit); -->
    <select id="findHotsTagIds" parameterType="int" resultType="java.lang.Long">
        SELECT tag_id FROM `ms_article_tag` group by tag_id order by count(*) desc limit #{limit}
    </select>

        <!--  List<Tag> findTagsByTagIds(List<Long> tagIds);  -->
        <select id="findTagsByTagIds" parameterType="list" resultType="com.hzc.blog.dao.pojo.Tag">
            select id,tag_name as tagName from ms_tag
            where id in
            <foreach collection="tagIds" item="tagId" separator="," open="(" close=")">
                #{tagId}
            </foreach>
        </select>
</mapper>
```

### 小结

#### mybatisplus遇到多表查询

建立TagMapper后需要建立xml文件进行读写

<img src="/Users/hzc/Library/Application Support/typora-user-images/image-20211208213631346.png" alt="image-20211208213631346" style="zoom:80%;" />

#### 创建文件夹时

使用IDEA创建多级文件夹时，文件夹名为`com.hzc.blogapi.dao.mapper`和`com/hzc/blogapi/dao/mapper`均会显示：`com.hzc.blogapi.dao.mapper`，但是会一个是单文件夹，一个是文件夹嵌套。



### 5. 统一异常处理

不管是controller层还是service，dao层，都有可能报异常，如果是预料中的异常，可以直接捕获处理，如果是意料之外的异常，需要统一进行处理，进行记录，并给用户提示相对比较友好的信息。

` com.hzc.blogapi.handler.AllExceptionHandler`

```java
package com.hzc.blogapi.handler;

import com.hzc.blogapi.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 对家了@Controller注解的方法进行拦截处理 AOP 的实现
@ControllerAdvice
public class AllExceptionHandler {
    // 进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody   // 返回json数据
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
```


