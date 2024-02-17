package liuyang.testspringbootenv.common.exception;

import liuyang.testspringbootenv.common.enums.ErrorEnum;
import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理（DispatcherServlet流程控制下发生的异常）
 *
 * @author liuyang
 * @scine 2021/3/31
 *        2022/5/16 增加BeanValidation的统一验证处理
 *        2024/2/18 证实BeanValidation的同意验证方案依据。看一下MethodArgumentNotValidException的继承结构。
 *                  动力节点 https://www.bilibili.com/video/BV1Km4y1k7bn/?p=157&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 */
@RestControllerAdvice
@Slf4j
public class
RestControllerExceptionAdvice {

    // 依据1： 灰_灰 BeanValidation视频 3. 异常处理、级联验证 的前半部分（00:00 ~ 30:38）讲得就是一个全局异常处理方案。
    // 一般为BeanValidation时抛出 20220516 add
    //@ExceptionHandler(org.springframework.validation.BindException.class)

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //return style1(e);
        return style2(e);
    }

    private R style1(MethodArgumentNotValidException e) {
        //log.error(e.getMessage(), e);
        StringBuilder errorInfo = new StringBuilder();
        // 前端友好命名：
        errorInfo.append(e.getBindingResult().getTarget().getClass().getSimpleName());// ok
        // 服务端友好命名：
        //errorInfo.append(e.getBindingResult().getTarget().getClass().getCanonicalName());// ok
        errorInfo.append(" : ");
        errorInfo.append(
                e.getBindingResult()
                        //.getAllErrors() // 这样写的话 error中拿不到field
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + " -> " + error.getDefaultMessage())
                        .collect(Collectors.joining(",", "", "")));
        // 另注 begin
        // .collect(Collectors.toMap(FieldError::getField, FieldError:getDefaultMessage))
        // R.error也应该增加一个含Map的方法。
        // 另注 end
        return R.error(ErrorEnum.MethodArgumentNotValid.getCode(), errorInfo.toString());
    }

    private R style2(MethodArgumentNotValidException e) {
        //log.error(e.getMessage(), e);
        Map<String, String> errorInfo = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return R.error(ErrorEnum.MethodArgumentNotValid.getCode(), ErrorEnum.MethodArgumentNotValid.getMsg(), errorInfo);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        //log.error(e.getMessage(), e);
        return R.error(ErrorEnum.MethodNotSupported.getCode(),  ErrorEnum.MethodNotSupported.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(ErrorEnum.ServerError.getMsg());
    }
}
