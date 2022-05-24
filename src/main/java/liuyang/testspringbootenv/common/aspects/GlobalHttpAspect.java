package liuyang.testspringbootenv.common.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截所有Controller的http请求
 * 1. 给每一个请求计时
 * 2. 审计（日志）
 *
 * @author liuyang(wx)
 * @since 2022/5/24
 */
@Aspect
@Component
public class GlobalHttpAspect {
    private static final Logger log = LoggerFactory.getLogger(GlobalHttpAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //@Pointcut("execution(public * liuyang.*.controller.*.*(..))")// 切了个寂寞
    // 可用，不过对不同层次的controller的估计还是失效。
    @Pointcut("execution(public * liuyang.testspringbootenv.modules.*.controller.*.*(..))")
    public void allControllerMethods() {}

    @Before("allControllerMethods()")
    public void doBefore(JoinPoint joinPoint) {
        // 计时开始
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.debug("url={}, method={}, ip={}, class_method={}"
                    , request.getRequestURI()
                    , request.getMethod()
                    , request.getRemoteAddr()
                    , joinPoint.getSignature().getDeclaringType() + "," + joinPoint.getSignature().getName());
            log.debug("request={}", joinPoint.getArgs());
        }
    }

    @AfterReturning(pointcut = "allControllerMethods()", argNames = "joinPoint, object", returning = "object")
    public void doAfterReturning(JoinPoint joinPoint, Object object) {
        // 计时结束
        long timeConsuming = System.currentTimeMillis() - startTime.get();
        log.debug("timeConsuming: {}ms", timeConsuming);
    }
}
