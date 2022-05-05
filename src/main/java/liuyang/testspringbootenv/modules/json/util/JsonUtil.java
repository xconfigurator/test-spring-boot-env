package liuyang.testspringbootenv.modules.json.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedList;

/**
 * 为与海能达对接时使用Fastjson特性定制工具类
 * @author liuyang(wx)
 * @since 2022/4/28
 */
public class JsonUtil {

    private static LinkedList<SerializerFeature> serializerFeatures;
    static {
        serializerFeatures = new LinkedList<>();
        //serializerFeatures.add(SerializerFeature.PrettyFormat);
        serializerFeatures.add(SerializerFeature.WriteMapNullValue);
        serializerFeatures.add(SerializerFeature.DisableCircularReferenceDetect);
        // 日期
        // @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
        serializerFeatures.add(SerializerFeature.WriteDateUseDateFormat);
        // 加之前：{"bd":14632796719163961436738196341296219,"d":1.1,"id":1,"info":"foo 中文！","name":"liuyang","nullProperty":null,"testDate":1651112665061,"testJSR310Date":"2022-04-28T10:24:25.067745900"}
        // 加之后：{"bd":14632796719163961436738196341296219,"d":1.1,"id":1,"info":"foo 中文！","name":"liuyang","nullProperty":null,"testDate":"2022-04-28 10:27:07","testJSR310Date":"2022-04-28 10:27:07"}
        //serializerFeatures.add(SerializerFeature.UseISO8601DateFormat);
    }

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj, serializerFeatures.toArray(new SerializerFeature[]{}));
    }

    public static <T> T parseObject(String jsonPrimitive, Class<T> clazz) {
        return JSON.parseObject(jsonPrimitive, clazz);
    }
}
