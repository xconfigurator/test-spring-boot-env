# 参考其他全局异常处理样式 20220525

```java
@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    /**
     * 全局异常处理，所有Exception
     *
     * @param e 异常
     * @return 标准返回类型
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResult exceptionGet(Exception e) {
        RestResult result = new RestResult(e);
        LOGGER.error("【未知异常】", e);
        return result;
    }

    /**
     * 服务异常
     *
     * @param req 用户请求
     * @param e 服务异常
     * @return 标准返回数据
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public RestResult handleServcieException(HttpServletRequest req, ServiceException e) {
        LOGGER.warn("【服务异常】code:{},message{}", e.getCode(), e.getMessage(), e);
        LOGGER.warn("【服务异常】url:{},message{}", req.getRequestURL(), e.getMessage(), e);
        return RestResult.error(e);
    }
}


```