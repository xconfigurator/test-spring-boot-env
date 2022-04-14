package liuyang.testspringbootenv.modules.json.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 示例
 * @author liuyang(wx)
 * @since 2022/4/14
 */
@Data
@ToString
public class R<T> {
    private Boolean success = Boolean.TRUE;
    private int code;
    private T data;
    private R() {}

    public static <T> R<T> ok(T t) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setSuccess(true);
        r.setData(t);
        return r;
    }
}
