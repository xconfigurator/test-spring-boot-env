package liuyang.testspringbootenv.modules.security.springsecurity.util;

import com.alibaba.fastjson.JSON;
import liuyang.testspringbootenv.common.utils.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在Filter链中没有MVC的Converter，所以转换成
 * @author liuyang(wx)
 * @since 2022/3/28
 */
public class RespHelper {

    public static HttpServletResponse setJSONMeta(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        return response;
    }

    public static void printJSONObj(HttpServletResponse response, Object obj) throws IOException {
        setJSONMeta(response);
        response.getWriter().println(JSON.toJSON(obj));
        response.getWriter().flush();
    }

    public static void printJSONError(HttpServletResponse response, String err) throws IOException {
        printJSONObj(response, R.error(err));
    }

    public static void printJSONMessage(HttpServletResponse response, String msg) throws IOException {
        printJSONObj(response, R.ok(msg));
    }
}
