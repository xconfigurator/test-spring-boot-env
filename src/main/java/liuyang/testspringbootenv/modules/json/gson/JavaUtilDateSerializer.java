package liuyang.testspringbootenv.modules.json.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * 参考：https://stackoverflow.com/questions/6873020/gson-date-format
 * @author liuyang(wx)
 * @since 2022/4/13
 */
public class JavaUtilDateSerializer implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return date == null ? null : new JsonPrimitive(date.getTime());
    }
}