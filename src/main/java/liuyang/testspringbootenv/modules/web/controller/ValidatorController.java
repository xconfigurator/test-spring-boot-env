package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.json.util.JsonUtil;
import liuyang.testspringbootenv.modules.web.vo.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * 测试 BeanValidator JSR 303/ JSR 349/ JSR 380
 * 参考视频 灰_灰
 * 注意1：
 * @Validated标注在待校验参数上。
 * 注意2：
 * post一个完整的JSON，参数接收的时候应该使用：@RequestBody标注参数名称前。
 *
 * @author liuyang
 * @since 2021/6/22
 *        2022/5/16 配合HttpClient 4测试用例完成第一个完整的校验流程。
 */
@RestController
@RequestMapping("/valid")
@Slf4j
public class ValidatorController {

    @GetMapping
    public R hello() {
        return R.ok("hello Spring Boot Env test BeanValidator JSR 303/ JSR 349/ JSR 380");
    }

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
