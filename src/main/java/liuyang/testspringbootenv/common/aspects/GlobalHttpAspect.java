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
        //attributes = null; //仅使用这句而保留前面的startTime.set(System.currentTimeMillis());会打印如下日志，符合预期。202207181031
        /*
        2022-07-18 10:30:09.919 [http-nio-80-exec-1] ERROR l.t.common.aspects.GlobalHttpAspect - 无法对请求进行计时操作
        2022-07-18 10:30:09.921 [http-nio-80-exec-1] DEBUG l.t.common.aspects.GlobalHttpAspect - timeConsuming: 2ms
         */
        if (null == attributes) {
            // 拿不到大不了就不审计了，不影响正常流程。
            log.error("无法对请求进行计时操作");
            // return;// 这个return不需要加  SonaLint: Jump statements should not be redundant
        } else {
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
        if (null == startTime.get()) return;// 没有开始时间，计时也就无意义了。
        long timeConsuming = System.currentTimeMillis() - startTime.get();
        log.debug("timeConsuming: {}ms", timeConsuming);
    }
}
