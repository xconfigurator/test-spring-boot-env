package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.vo.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 测试 BeanValidator JSR 303/ JSR 349/ JSR 380
 * 参考视频 灰_灰
 *
 * @author liuyang
 * @since 2021/6/22
 */
@RestController
@RequestMapping("/valid")
@Validated// 注意点1
@Slf4j
public class ValidatorController {

    @GetMapping
    public R hello() {
        return R.ok("hello Spring Boot Env test BeanValidator JSR 303/ JSR 349/ JSR 380");
    }

    @PostMapping("/department")
    public R validDepartment(
            @RequestBody @Valid Department department
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getModel().toString());
            return R.error("校验失败");
        }
        log.info("校验成功");
        return R.ok("校验成功");
    }


}
