package liuyang.testspringbootenv.controller;

import liuyang.testspringbootenv.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang
 * @scine 2021/3/30
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/sync")
    public String doSomething() throws Exception {
        return asyncService.doSomething();
    }

    @GetMapping("/async")
    public String doSomethingAsync() throws Exception {
        asyncService.doSomethingAsync();
        return "Async Service Returned!";
    }
}
