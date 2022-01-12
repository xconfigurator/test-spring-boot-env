# 测试Spring Boot集成

### 说明
启动项目前需要先启动本地的Redis服务。  
Spring Session Data Redis (2021/4/13已配置)
Spring Cache (TODO)
测试从PC提交， 注意: GitHub上的项目clone时最好选择ssh（在注册了SSH key之后）  
202106221012 增加Bean Validation

### 主要参考视频  
Spring Boot  
[雷丰阳]()
Spring Security  
[雷丰阳]()  
[ES搜房网]()
Bean Validation
[参考视频](https://www.bilibili.com/video/BV1UE411t7BZ/?spm_id_from=333.788.recommend_more_video.0)

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
>配置代码在test-spring-boot-env  
>spring-boot-starter-cache
>一句话简述：两个关键点，第一是缓存相关注解，第二是KeyGenerator或者key（保障业务逻辑正确：查询的key和更新操作的key要一致）。
```
// 小结一下：
// 1. select 查询操作：
@Cacheable(cacheNames = {"actor"}, key = "#actor.id")
// 2. update 更新操作：CachePut两种写法等价
@CachePut(cacheNames = {"actor"}, key = "#actor.id")
@CachePut(cacheNames = {"actor"}, key = "#result.id")
// 3. delete 删除操作
@CacheEvice(value = "actor", key = "#id") // value与cacheNames等价
或
@CacheEvict(cacheNames = {"actor"}, key = "#actor.id")
清空指定缓存中的所有数据
@CacheEvict(cacheNames = {"actor"}, allEntries = true)
// 4. 复杂情况使用@Caching
// 5. 在类（Service）上配置缓存的公共属性@CacheConfig (前面的1-4都是配置在方法上的。)
``` 
01. JSR107

02. Spring 缓存抽象简介

03. 搭建基本实验环境（Spring Boot + MyBatis）
    - 使用SpringBoot + MyBatis-Plus + MyBatis-Plus-Generator 可以更方便地配合

04. @Cachable 初体验
    - 将@Cacheable注解在方法上 （方法返回值会被返回）
    - 由于使用Generator生成，可以选择继承相对应的方法，重写之，加上@Cacheable的方式进行。
        - 1. 将原Generator生成的Impl的@Service注解取消。改在将自定义的继承类注册进容器。
        - 2. 调用处，如果使用@Autowire默认的ByType策略测不需要任何修改容器即可完成注册。
    - 详细的参见 test-spring-boot-env下的liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.service.impl.ActorServiceImplCacheable代码注释

05. @Cacheable 工作流程和缓存工作原理 pause <--------- TODO
    - 调用时机：调用方法之前看看缓存中有没有指定Key的数据

06. @Cacheable 其他（除了cacheNames之外的其他属性） pause <--------- TODO

07. @CachePut
    - 适用方法：update，add(未测试， 预计应该配合 key = "#result.id", 而不能使用 key = "#actor.id"， 因为持久化之前id是空的。)
    - 调用时机：调用方法之后，把方法结果放入缓存中。
    - 调用行为：即调用方法又更新缓存（适用于更新类操作）

08. @CacheEvict
    - 适用方法：delete
    - 调用行为：清除缓存中的相应对象。
    - 调用时机：默认是调用方法之后。
      beforeInvocation默认未false。可以通过指定值来改变行为。
      默认情况如果方法执行时抛出异常，则不清空缓存。
      若裴志伟true，则无论方法是否出现异常，都会清理缓存。
    - 说明：当然也可以不配合数据库操作，单纯注解在一个Controller方法上完成对缓存的删除操作。
        - allEntries = true时会清空指定缓存。

09. @Caching & @CacheConfig
    - @Cacheing是@Cacheable @CachePut @CacheEvict三个注解的组合。用以应对复杂场景（使用之前再翻一下视频）。
```
示例：未测试
@Caching(
    cacheable = {
        @Cacheable(value = "emp", key = "#lastName")
    },
    put = {
        @CachePut(value = "emp", key = "#result.id"),
        @CachePut(value = "emp", key = "#result.email")
    }
)
```
    - 在类（Service）上配置缓存的公共属性@CacheConfig (前面的1-4都是配置在方法上的。)
        - cacheNames
        - keyGenerator
        - cacheManager
        - cacheResolver

10. 搭建Redis环境
    - 若没有配置Redis基础设施的时候，Spring Boot使用的是ConcurrentMapCacheManager。
    - Docker环境， RedisDeaktopManager使用演示。

11. RedisTemplate
    - spring-boot-starter-data-redis
    - 工具类：StringRedisTemplate, RedisTemplate
```shell
        stringRedisTemplate.opsForValue();// strings
        stringRedisTemplate.opsForHash();// hashes
        stringRedisTemplate.opsForList();// lists
        stringRedisTemplate.opsForSet();// set
        stringRedisTemplate.opsForZSet();// sorted sets
```

12. 自定义CacheManager
    - 引入spring-boot-starter-data-redis (前提：spring-boot-starter-cache)
    - 修改默认的序列化器（默认的是JDK的序列化，修改为使用JSON）
        - Spring Boot 1.x 看了一遍，并没有测试。
            - 1. 序列化为JSON
            - 2. 操作缓存的方法：第一，使用注解；第二，使用硬编码。
        - Spring Boot 2.x
            - 测试PDT项目中suhj的方案。测试知这个是订制了RedisTemplate， 影响直接使用RedisTemplate的方法。
                - 使用订制的RedisTemplate存储JSON正常！
                - 貌似不影响注解的缓存，怎么办？？？ // TODO
            - [参考文档 Spring Boot2.X 自定义Redis的cacheManager，保存Json格式到Redis](https://www.pianshen.com/article/531127825/) // TODO 待验证
                - 文章方法验证失败。 20210415

99. 重要补充：LocalDateTime （若使用默认的JDK序列化不需要配置这些，序列化和反序列化都正常。）
    [关于Jackson和LocalDateTime转换的问题](https://www.cnblogs.com/yzeng/p/11522411.html)
```shell
方案测试ok
2021-04-15 14:14:19.518  INFO 19816 --- [           main] liuyang.testdataredis.RedisTemplateTest  : Actor(actorId=4, firstName=yang, lastName=liu, lastUpdate=2021-04-15T14:14:17)
```


### data.*
### data.redis
### data.elasticsearch
细节测试参见独立的test-data-*项目
OK redis  
TODO elasticsearch TODO

### job
OK modules/job

### security.springsecurity
OK modules/security/springsecurity


### dubbo
modules/dubbo

### amqp
细节测试参见独立的 test-messaging
OK RabbitMQ

### 消息：其他
TODO Kafka

### 分布式
参见周阳的Spring Cloud Alibaba

### 热部署
貌似有商业解决方案

### 监控
参见Spring Boot 2.x的方案

### Bean Validation
[参考视频](https://www.bilibili.com/video/BV1UE411t7BZ/?spm_id_from=333.788.recommend_more_video.0)
202106221012 增加Bean Validation  
暂时转移到data-server下实验。那里没有Spring Security干扰。
Bean Validation还可以参见李兴华老师的视频。

### ssl
[参考视频]()
202112271455 增加SSL配置。笔记见为知笔记。
 modules/security/ssl
李兴华老师的在Spring Boot工程中开启SSL。