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

  











