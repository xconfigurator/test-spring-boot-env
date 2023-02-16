package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.vo.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.joining;

/**
 * 测试 BeanValidator JSR 303/ JSR 349/ JSR 380
 * 参考视频 灰_灰
 * 注意1：
 * @Validated标注在待校验参数上。
 * 注意2：
 * post一个完整的JSON，参数接收的时候应该使用：@RequestBody标注参数名称前。
 *
 * 灰_灰视频：
 * https://www.bilibili.com/video/BV1UE411t7BZ/?spm_id_from=333.788.recommend_more_video.0&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 乐之者Java视频：
 * https://www.bilibili.com/video/BV17i4y157Ah?p=16&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @Valid: 不能指定分组
 * @Validated: 可以指定分组
 * https://www.bilibili.com/video/BV17i4y157Ah?p=18&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * https://www.bilibili.com/video/BV17i4y157Ah?p=20&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author liuyang
 * @since 2021/6/22
 *        2022/5/16 配合HttpClient 4测试用例完成第一个完整的校验流程。
 */
@RestController
@RequestMapping("/valid")
@Slf4j
// @Validated // 表示整个类都启动校验，如果碰到入参含有Bean Validation注解的话，就会自动校验。
public class ValidatorController {

    @GetMapping
    public R hello() {
        return R.ok("hello Spring Boot Env test BeanValidator JSR 303/ JSR 349/ JSR 380");
    }

    // 最简单使用流程 - 配合全局处理异常（推荐）
    // 全局处理异常代码参见：liuyang.testspringbootenv.common.exception.RestControllerExceptionHandler.handleMethodArgumentNotValidException
    @PostMapping("/department2")
    public R validDepartment2(
            @RequestBody @Validated Department department) {
        // 尝试走全局异常处理流程
        log.info("校验成功");
        return R.ok("校验成功");
    }

    // 最简单使用流程 - 局部处理异常
    @PostMapping("/department")
    public R validDepartment(
            @RequestBody @Validated Department department
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // style1
            //log.error(bindingResult.getModel().toString());
            //return R.error("校验失败");

            // style2 - 定制简要失败信息
            String errorInfo = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(joining(",", "", ""));
            log.error("校验失败");
            //log.error(bindingResult.getModel().toString());
            return R.error(errorInfo);

            // style3 - 返回所有校验错误信息
            //String allErrorInfo = JsonUtil.toJSONString(bindingResult.getModel().toString());
            //return R.error(allErrorInfo);
        }
        log.info("校验成功");
        return R.ok("校验成功");
    }

}
