package liuyang.testspringbootenv.modules.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import liuyang.testspringbootenv.modules.json.vo.Person;
import liuyang.testspringbootenv.modules.json.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * https://github.com/alibaba/fastjson
 *
 * 参考视频：TODO
 * 参考文档：https://www.runoob.com/w3cnote/fastjson-intro.html
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

    // 序列化
    // JSON.toJSONString([obj]);
    @Test
    void testSerialization() {
        // 最简示例
        log.info("person = {}", JSON.toJSONString(p));

        // Pretty 方法2
        log.info("person = {}", JSON.toJSONString(p, SerializerFeature.PrettyFormat));

        // /////////////////////////////////////////////////////
        // 序列化选项
        // 1. 包含null
        // JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue)
        log.info("person = {}", JSON.toJSONString(p, SerializerFeature.WriteMapNullValue));
        log.info("person = {}", JSON.toJSONString(p, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat));// 多SerializerFeature
        // 2. 指定日期格式 默认就是标准的JSR 310 DateTimeFormatter.ISO_DATE_TIME 但可定制
        // @JSONField(format = "yyyy-MM-dd HH:mm:ss") (java.util.Data java.time.LocalDateTime均适用)

        // 3. 禁用循环引用探测
        // JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
        // 4.  SerializeFilter定制处理（对属性或属性值在序列化前做定制化处理）
        // 需求：key大写
        /*
        NameFilter nameFilter = (obj, name, val) -> {
            return name.toUpperCase();
        };
        JSON.toJSONString(obj, nameFilter);
         */
    }

    // 反序列化
    // JSON.parse([jsonStr], [Clazz]);
    @Test
    void testDeserialization() {
        // jsonStr = JSON.toJSONString(p);
        String jsonStr = "{\"bd\":14632796719163961436738196341296219,\"d\":1.1,\"id\":1,\"info\":\"foo 中文！\",\"name\":\"liuyang\",\"testDate\":1649822863847,\"testJSR310Date\":\"2022-04-13T12:07:43.858927600\"}";
        log.info("person = {}", JSON.parseObject(jsonStr, Person.class));

        // 多了一个id2， Fastjson不配置，就默认忽略vo中不存在的字段。（Jackson如果默认不配置则会报错）
        String jsonStr2 = "{\"id\":1,\"id2\":1,\"name\":\"liuyang\",\"info\":\"foo 中文！\",\"testDate\":1649829675288,\"testJSR310Date\":\"2022-04-13T14:01:15.294863\",\"d\":1.1,\"bd\":14632796719163961436738196341296219}";
        log.info("person = {}", JSON.parseObject(jsonStr2, Person.class));
    }

    // /////////////////////////////////////////////////////
    // 反序列化带泛型的VO 02:39 左右 TypeReference
    // ResultVO<T> <-- 自定义泛型类， VO是Person类
    // ResultVO<Person> person = JSON.parseObject(jsonStr, new TypeReference<ResultVO<Person>>(){});
    // 反序列化泛型
    @Test
    void testDeserializationR() {
        // 序列化
        R<Person> result = R.ok(p);
        String jsonPrimitive = JSON.toJSONString(result);
        log.info("json = {}", jsonPrimitive);

        // 反序列化
        R<Person> r = JSON.parseObject(jsonPrimitive, new TypeReference<R<Person>>(){});
        log.info("r = {}", r);
    }
}
