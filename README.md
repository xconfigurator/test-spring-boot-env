# 测试Spring Boot集成

### 说明
启动项目前需要先启动本地的Redis服务。  
Spring Session Data Redis (2021/4/13已配置)
Spring Cache (TODO)


###
```shell
…or create a new repository on the command line
echo "# test-spring-boot-env" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:xconfigurator/test-spring-boot-env.git
git push -u origin main

…or push an existing repository from the command line
git remote add origin git@github.com:xconfigurator/test-spring-boot-env.git
git branch -M main
git push -u origin main

…or import code from another repository
You can initialize this repository with code from a Subversion, Mercurial, or TFS project.
```
### TODO 把雷丰阳老师的Spring Boot的PPT搞出来！


### session
[看雷丰阳的电商项目](https://www.bilibili.com/video/BV1np4y1C7Yf?p=225)
- Spring Session 雷丰阳
- 225 问题引入 （可以不再看了）
- 226 分布式session解决方案 （可以不再看了）
   - session集中存储方案 发卡的时候选择父域名
- 227 Spring Session整合
   - https://docs.spring.io/spring-session/docs/2.4.2/reference/html5/guides/boot-redis.html
   - 配置
      1. @EnableRedisHttpSession
      2. spring.session.store-type=redis
      3. 检查redis链接配置
      4. 原来怎么用session，现在还怎么用session，就这么简单。后台方面，session已经被Spring Session托管。
   - 注意vo序列化
      - implements Serializable    
- 228 Spring Session完成子域session共享
   - 解决令牌作用域问题（解决子域session共享问题）
      - https://docs.spring.io/spring-session/docs/2.4.2/reference/html5/#api-cookieserializer
   - 可选项：使用JSON方式序列化到Redis（默认使用的是JDK的序列化）
      - https://github.com/spring-projects/spring-session/tree/2.4.2/spring-session-samples/spring-session-sample-boot-redis-json
         - SessionConfig.java
- 229 Spring Session核心原理
    

### cache
[参考视频](https://www.bilibili.com/video/av23284778)  
1. JSR-107 p1
2. Spring缓存抽象 <-- 实际应用中使用的是它
    - since Spring 3.1
        - org.springframework.cache.CacheManager
        - org.springframework.cache.Cache
    - p2 - p
        

3. 整合Redis

### data.*
### data.redis
### data.elasticsearch
细节测试参见独立的test-data-*项目


### job


### security.springsecurity


### amqp


### 分布式


### 热部署


### 监控

