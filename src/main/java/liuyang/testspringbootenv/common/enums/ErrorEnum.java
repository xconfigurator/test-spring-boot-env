package liuyang.testspringbootenv.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author liuyang(wx)
 * @since 2022/5/16
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {
    ServerError(500, "服务器内部错误")
    , MethodNotSupported(80403, "没有该方法")
    , MethodArgumentNotValid(80888, "参数校验失败");

    private int code;
    private String msg;

    // 测试一下
    public static void main(String[] args) {
        System.out.println(ErrorEnum.MethodArgumentNotValid.getCode());
        System.out.println(ErrorEnum.MethodArgumentNotValid.getMsg());
        System.out.println(ErrorEnum.MethodArgumentNotValid.ordinal());
        System.out.println(ErrorEnum.MethodArgumentNotValid.name());
    }
}
