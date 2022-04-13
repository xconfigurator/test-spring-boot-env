package liuyang.testspringbootenv.modules.json;

import com.alibaba.fastjson.JSON;
import liuyang.testspringbootenv.modules.json.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * https://github.com/alibaba/fastjson
 *
 * 参考视频：TODO
 *
 * @author liuyang(wx)
 * @since 2022/4/12
 */
@Slf4j
public class FastjsonTest {

    static Person p = new Person();// precision 精度
    static {
        p.setId(1l);
        p.setName("liuyang");
        p.setInfo("foo 中文！");
        p.setTestDate(new Date());
        p.setTestJSR310Date(LocalDateTime.now());
        p.setD(1.1d);
        p.setBd(new BigDecimal("14632796719163961436738196341296219"));
    }

    // 序列化
    // JSON.toJSONString([obj]);
    @Test
    void testSerialization() {
        log.info("person = {}", JSON.toJSONString(p));
    }

    // 反序列化
    // JSON.parse([jsonStr], [Clazz]);
    @Test
    void testDeserialization() {
        // jsonStr = JSON.toJSONString(p);
        String jsonStr = "{\"bd\":14632796719163961436738196341296219,\"d\":1.1,\"id\":1,\"info\":\"foo 中文！\",\"name\":\"liuyang\",\"testDate\":1649822863847,\"testJSR310Date\":\"2022-04-13T12:07:43.858927600\"}";
        log.info("person = {}", JSON.parseObject(jsonStr, Person.class));
    }
}
