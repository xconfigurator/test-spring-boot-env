package liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.entity.Actor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author liuyang
 * @scine 2021/4/14
 */
@Service
@Slf4j
public class ActorServiceImplCacheable extends ActorServiceImpl{

    /**
     * 将方法的结果进行缓存。
     *
     * 使用注意！：还是需要拿到查询的业务参数，否则这个缓存没有意义！
     * TODO 如何从queryWrapper中拿到业务参数？ 比如查询参数，id等
     * 方案1：从queryWrapper中找办法 TODO 待找办法
     * 方案2：编码业务Service调用MyBatis-Plus Generator 生成好的 Service的方式，然后将缓存注解注到业务Service的上面（也就是说不是本利这种方式） TODO 待验证
     *
     * 说明：关于@Cacheable
     * 属性：cacheNames/ value 用于指定缓存组件的名字（即数据放到哪里去）
     *      CacheManger管理多个Cache组件，对缓存中真正的CRUD操作在Cache组建中进行。
     * 属性：key 缓存数据使用的key。
     *      如果不指定，默认使用 [方法参数值]-[方法返回值] 的方式存放在缓存中。
     * 属性：keyGenerator key的生成器。
     *      key和keyGenerator二选一。
     * 属性：cacheManager 指定缓存管理器
     *      cacheManager和cacheResolver二选一。
     * 属性：condition 指定符合条件的情况下才缓存。可以使用SpEL 如：condition = "#id>0" 当被缓存对象的id大于0的时候才缓存。
     * 属性：unless 否定缓存的。当unless指定的条件满足时，则不缓存。
     * 属性：sync 是否使用异步模式。
     *
     * @param queryWrapper
     * @return
     */
    @Cacheable(cacheNames = {"actor"}, keyGenerator = "testCachAvailabilityKeyGenerator")
    // @Cacheable(cacheNames = {"actor"}, keyGenerator = "testCachAvailabilityKeyGenerator")
    // @Cacheable(cacheNames = {"actor"}, keyGenerator = "uuidKeyGenerator")
    // @Cacheable(cacheNames = "", key = "#id") // SpEL。 key指定为参数queryWrapper。 具体参看Cache SpEl available metadata
    @Override
    public Actor getOne(Wrapper<Actor> queryWrapper) {
        // 看看哪个参数做key合适(断点貌似不好使，总是运行卡住)
        log.debug("queryWrapper.getCustomSqlSegment() = " + queryWrapper.getCustomSqlSegment());
        log.debug("queryWrapper.getSqlComment() = " + queryWrapper.getSqlComment());
        log.debug("queryWrapper.getSqlFirst() = " + queryWrapper.getSqlFirst());
        log.debug("queryWrapper.getSqlSelect() = " + queryWrapper.getSqlSelect());
        log.debug("queryWrapper.getSqlSet() = " + queryWrapper.getSqlSet());
        log.debug("queryWrapper.getSqlComment() = " + queryWrapper.getSqlComment());
        log.debug("queryWrapper.getSqlSegment() = " + queryWrapper.getSqlSegment());
        log.debug("queryWrapper.getTargetSql() = " + queryWrapper.getTargetSql());
        log.debug("queryWrapper.getExpression().toString() = " + queryWrapper.getExpression().toString());
        return super.getOne(queryWrapper);
    }
}
