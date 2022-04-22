package liuyang.testspringbootenv.modules.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import liuyang.testspringbootenv.modules.json.gson.JSR310LocalDateTimeDeserializer;
import liuyang.testspringbootenv.modules.json.gson.JSR310LocalDateTimeSerializer;
import liuyang.testspringbootenv.modules.json.gson.JavaUtilDateDeserializer;
import liuyang.testspringbootenv.modules.json.gson.JavaUtilDateSerializer;
import liuyang.testspringbootenv.modules.json.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * https://github.com/google/gson
 *
 * 参考文档：https://geek-docs.com/java/java-tutorial/gson.html
 *
 * 参考视频：TODO
 *
 * @author liuyang(wx)
 * @since 2022/4/12
 */
@Slf4j
public class GsonTest {
    static Gson gson;
    static Person p = new Person();// precision 精度
    static {
        p.setId(1l);
        p.setName("liuyang");
        p.setInfo("foo 中文！");
        p.setNullProperty(null);
        p.setTestDate(new Date());
        p.setTestJSR310Date(LocalDateTime.now());
        p.setD(1.1d);
        p.setBd(new BigDecimal("14632796719163961436738196341296219"));

        // 定制Gson
        /**
         * Gson对外可见的构造函数是默认构造函数，默认了JSON的各种特性。有参构造函数是default类型，对外不可见。
         * 通过GsonBuilder，可以按需设置Gson的特性，然后调用create方法得到具体具有自定义特性的Gson对象。
         */
        gson =  new GsonBuilder()
                // Pretty
                .setPrettyPrinting()
                // 1. null处理, 默认不包含null。 Fastjson默认不包含null。Jackson，默认输出null
                .serializeNulls()
                // 2. 日期格式处理
                // java.util.Date
                .registerTypeAdapter(java.util.Date.class, new JavaUtilDateSerializer())
                .registerTypeAdapter(java.util.Date.class, new JavaUtilDateDeserializer())
                // JSR 310 LocalDateTime
                .registerTypeAdapter(LocalDateTime.class, new JSR310LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new JSR310LocalDateTimeDeserializer())
                .create();
    }

    // toJson
    @Test
    void testSerialization() {
        log.info("person = {}", gson.toJson(p));
    }

    // fromJson
    @Test
    void testDeserialization() {
        // 使用Fastjson序列化（failure）定制Date和LocalDateTime格式之后OK
        //String jsonStr = "{\"bd\":14632796719163961436738196341296219,\"d\":1.1,\"id\":1,\"info\":\"foo 中文！\",\"name\":\"liuyang\",\"testDate\":1649822863847,\"testJSR310Date\":\"2022-04-13T12:07:43.858927600\"}";
        // 使用Gson（默认）序列化（ok） 定制Date和LocalDateTime格式之后failure
        //String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":\"Apr 13, 2022, 12:16:11 PM\",\"testJSR310Date\":{\"date\":{\"year\":2022,\"month\":4,\"day\":13},\"time\":{\"hour\":12,\"minute\":16,\"second\":11,\"nano\":386532700}},\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        // 使用Gson（定制）序列化
        // Jackson序列化 (falure)
        // String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649826634571,\"testJSR310Date\":[2022,4,13,13,10,34,577694700],\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        // Jackson序列化 订制后 ok
        String jsonStr = "{\"id\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649829675288,\"testJSR310Date\":\"2022-04-13T14:01:15.294863\",\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        log.info("person = {}", gson.fromJson(jsonStr, Person.class));

        // 关于泛型
        // Gson反序列化泛型对象的方法可能是TypeToken
        // 注：当键是复杂数据类型时，序列化反序列化时应指定属性：new GsonBuilder().enableComplexMapKeySerialization()
        // TypeToken使用示例：
        // Type type = new TypeToken<Map<Person, String>>(){}.getType();
        // gson.fromJson(json, type);
    }
}
