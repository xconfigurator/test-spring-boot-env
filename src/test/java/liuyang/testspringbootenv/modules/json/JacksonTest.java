package liuyang.testspringbootenv.modules.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import liuyang.testspringbootenv.modules.json.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * https://github.com/FasterXML/jackson
 * 参考文档： https://juejin.cn/post/6844904166809157639
 * 参考文档： https://www.jianshu.com/p/d49dc8dff51a
 * 参考视频：TODO
 *
 * @author liuyang(wx)
 * @since 2022/4/12
 */
@Slf4j
public class JacksonTest {
    static ObjectMapper om = new ObjectMapper();
    static Person p = new Person();// precision 精度
    static {
        p.setId(1l);
        p.setName("liuyang");
        p.setInfo("foo 中文！");
        p.setTestDate(new Date());
        p.setTestJSR310Date(LocalDateTime.now());
        p.setD(1.1d);
        p.setBd(new BigDecimal("14632796719163961436738196341296219"));

        // JSR 310
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        om.registerModule(javaTimeModule);
    }

    @Test
    void testSerialization() throws JsonProcessingException {
        // Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: liuyang.testspringbootenv.modules.json.vo.Person["testJSR310Date"])
        log.info("person = {}", om.writeValueAsString(p));
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        // Jackson序列化 （定之前）ok
        //String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649826634571,\"testJSR310Date\":[2022,4,13,13,10,34,577694700],\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        // Jackson序列化（定制后） ok
        String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649829675288,\"testJSR310Date\":\"2022-04-13T14:01:15.294863\",\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        log.info("person = {}", om.readValue(jsonStr, Person.class));
    }
}
