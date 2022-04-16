package liuyang.testspringbootenv.modules.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import liuyang.testspringbootenv.modules.json.vo.Person;
import liuyang.testspringbootenv.modules.json.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * https://github.com/FasterXML/jackson
 * 参考文档： https://juejin.cn/post/6844904166809157639
 * 参考文档： https://www.jianshu.com/p/d49dc8dff51a
 * 参考文档：https://www.yiibai.com/jackson/
 * 参考视频：TODO
 *
 * @author liuyang(wx)
 * @since 2022/4/12
 */
@Slf4j
public class JacksonTest {
    static ObjectMapper om;
    static Person p = new Person();// precision 精度
    static {
        p.setId(1l);
        p.setName("liuyang");
        p.setInfo("foo 中文！");
        p.setTestDate(new Date());
        p.setTestJSR310Date(LocalDateTime.now());
        p.setD(1.1d);
        p.setBd(new BigDecimal("14632796719163961436738196341296219"));

        // 定制ObjectMapper
        om = new ObjectMapper();
        // Pretty 方法1
        om.configure(SerializationFeature.INDENT_OUTPUT, true);// 调试用
        // Rretty 方法2
        // 在om输出时om.writerWithDefaultPrettyPrinter().writeValueAsString(p)
        // 1. null 配置序列化时只包含非空属性 （默认是包含null值属性）
        // 方法1：在om处指定
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 方法2：在vo处指定
        // 或者在vo类上使用@JsonInclude(JsonInclude.NON_NULL)注解

        // 2. 定制日期格式 e.g. JSR 310
        // 默认是这个样的 [2022,4,13,13,10,34,577694700]
        // 处理java.time.LocalDateTime
        // 步骤1：在om处指定
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)); // DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));// DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        om.registerModule(javaTimeModule);
        // 步骤2：在vo属性上使用@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 同样适用于java.util.Date和java.time.LocalDateTime
        // 处理java.util.Date
        // 默认是这个样的 1649826634571 （date.getTime()）
        // 方法1：在om处指定
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 方法2：在vo属性上使用@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        // 如果不执行步骤1，则只能序列化java.util.Date

        // 3. 不序列化未知属性（Fastjson默认是不序列化json中包含的非vo对象，而jackson需要配置策略，否则报错）
        // 方法1 20220416 ok
        //om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 方法2
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // ///////////////////////////////////////////////
        // 指定属性名和json字符串key的对应关系
        // @JsonProperty("NEW_PROPERTY_NAME")

        // 忽略指定属性
        // @JsonIgnore

        // ///////////////////////////////////////////////
        // Jackson 特色配置
        // 驼峰-下划线
        //om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);// Fastjson也有类似属性

    }


    @Test
    void testSerialization() throws JsonProcessingException {
        // Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: liuyang.testspringbootenv.modules.json.vo.Person["testJSR310Date"])
        log.info("person = {}", om.writeValueAsString(p));

        // Pretty 方法2
        //log.info("person = {}", om.writerWithDefaultPrettyPrinter().writeValueAsString(p));
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        // Jackson序列化 （定之前）ok
        //String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649826634571,\"testJSR310Date\":[2022,4,13,13,10,34,577694700],\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        // Jackson序列化（定制后） ok
        String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649829675288,\"testJSR310Date\":\"2022-04-13T14:01:15.294863\",\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        log.info("person = {}", om.readValue(jsonStr, Person.class));

        // 忽略不存在的key
        // JSON中多一个id2 而VO中没有该属性 Jackson如果不配置则反序列化会出错
        // om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonStr2 = "{\"id\":1,\"id2\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649829675288,\"testJSR310Date\":\"2022-04-13T14:01:15.294863\",\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        log.info("person = {}", om.readValue(jsonStr2, Person.class));
    }

    // /////////////////////////////////////////////////////
    // 反序列化带泛型的VO 04:45 左右 TypeReference (注意到Jackson和Fastjson都叫TypeReference 但包位置不同，导入时应注意。)
    @Test
    void testDeserializationR() throws JsonProcessingException {
        // 序列化
        R<Person> result = R.ok(p);
        String jsonPrimitive = om.writeValueAsString(result);
        log.info("json = {}", jsonPrimitive);

        // 反序列化
        R<Person> r = om.readValue(jsonPrimitive, new TypeReference<R<Person>>(){});
        log.info("r = {}", r);

        // 对比Fastjson的
        /*
        // 序列化
        R<Person> result = R.ok(p);
        String jsonPrimitive = JSON.toJSONString(result);
        log.info("json = {}", jsonPrimitive);

        // 反序列化
        R<Person> r = JSON.parseObject(jsonPrimitive, new TypeReference<R<Person>>(){});
        log.info("r = {}", r);
         */
    }

    // Jackson实用方法，如果后面的对象有值则按后面的对象更新
    @Test
    void testUpdateValue() throws JsonMappingException {
        Person pNewValue  = new Person();
        pNewValue.setInfo("foo");

        Person person = om.updateValue(p, pNewValue);
        log.info("person = {}", person);
    }
}
