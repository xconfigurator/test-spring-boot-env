package liuyang.testspringbootenv.modules.json.util;

import liuyang.testspringbootenv.modules.json.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author liuyang(wx)
 * @since 2022/4/28
 */
@Slf4j
public class JsonUtilTest {
    static Person p = new Person();// precision 精度
    static {
        p.setId(1l);
        p.setName("liuyang");
        p.setInfo("foo 中文！");
        p.setNullProperty(null);// 默认是不序列化null属性的。配置JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue)后才序列化null属性
        p.setTestDate(new Date());
        p.setTestJSR310Date(LocalDateTime.now());
        p.setD(1.1d);
        p.setBd(new BigDecimal("14632796719163961436738196341296219"));

        // 通用配置
        // 1. 格式化(调试使用)
        // SerializerFeature.PrettyFormat
        // 2. 指定属性名和json字符串key的对应关系
        // @JSONField(name = "JSON key 名称")
        // 3. 忽略指定属性
        // @JSONField(serialize = false)
        // @JSONField(deserialize = false)
    }

    @Test
    void testToJson() {
        log.info(JsonUtil.toJSONString(p));
    }

    @Test
    void parseObject() {
        Person pDeserized = JsonUtil.parseObject(JsonUtil.toJSONString(p), Person.class);
        log.info("person = {}", pDeserized);
    }

    // 边界测试
    @Test
    void testParseObjectNull() {
        Person person = JsonUtil.parseObject(null, Person.class);
        log.info("person = {}", person);
        Assertions.assertNull(person);
    }

    // 边界测试
    @Test
    void testToJsonNull() {
        // 传入null， 输出null
        String jsonString = JsonUtil.toJSONString(null);
        log.info("json = {}", jsonString);
        //Assertions.assertNull(jsonString);// 看来这个确实有点误会！ 当然也可以通过封装处理满足这个条件。这并不似Fastjson的问题，而是返回null确实没意义。
        //Assertions.assertEquals("", jsonString);// 经过封装处理
        Assertions.assertEquals("null", jsonString);// Fastjson默认侧策略
    }
}
