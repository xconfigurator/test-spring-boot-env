package liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.entity.Actor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liuyang
 * @scine 2021/4/13
 */
@SpringBootTest
@Slf4j
public class ActorServiceTest {

    @Autowired
    private ActorService actorService;

    @Test
    public void test() {
        actorService.list().forEach(System.out::println);
    }

    @RepeatedTest(4)
    @Test
    public void testCache() {
        Actor actor = actorService.getOne(Wrappers.<Actor>lambdaQuery().eq(Actor::getActorId, 1));
        // 	ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        log.info(ReflectionToStringBuilder.toString(actor, ToStringStyle.MULTI_LINE_STYLE));
        // log.info(actor.toString());
    }
}
