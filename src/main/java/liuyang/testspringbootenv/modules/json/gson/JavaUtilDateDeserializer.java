package liuyang.testspringbootenv.modules.json.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * 参考：https://stackoverflow.com/questions/6873020/gson-date-format
 * @author liuyang(wx)
 * @since 2022/4/13
 */
public class JavaUtilDateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonElement == null ? null : new Date(jsonElement.getAsLong());
    }
}
