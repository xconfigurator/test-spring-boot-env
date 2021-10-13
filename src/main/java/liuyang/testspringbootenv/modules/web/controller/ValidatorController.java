package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.vo.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author liuyang
 * @since 2021/6/22
 */
@RestController
@RequestMapping("/valid")
@Slf4j
public class ValidatorController {

    @GetMapping
    public R hello() {
        return R.ok("hello Spring Boot Env test Validation");
    }

    @PostMapping("/department")
    public R validDepartment(@Valid Department department, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getModel().toString());
            return R.error("校验失败");
        }

        log.info("校验成功");
        return R.ok("校验成功");
    }
}
