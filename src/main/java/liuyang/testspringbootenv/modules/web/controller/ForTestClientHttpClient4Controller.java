package liuyang.testspringbootenv.modules.web.controller;

import com.alibaba.fastjson.JSON;
import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 配合test-client-httpclient4项目用
 * @author liuyang(wx)
 * @since 2022/4/11
 */
@RestController
@RequestMapping("test-client-httpclient4")
@Slf4j
public class ForTestClientHttpClient4Controller {

    @GetMapping
    public R foo() {
        return R.ok("hello for test-client-httpclient4");
    }

    @GetMapping("test1")
    public String testGetForm(UserDTO userDTO, HttpServletRequest httpServletRequest) {
        log.info("userDTO = {}", JSON.toJSONString(userDTO));
        printHeader(httpServletRequest);
        return "test1";
    }

    // 注意到方法签名与Get无异
    @PostMapping("test2")
    public String testPostForm(UserDTO userDTO, HttpServletRequest httpServletRequest) {
        log.info("userDTO = {}", JSON.toJSONString(userDTO));
        printHeader(httpServletRequest);
        return "test2";
    }

    @PostMapping("test3")
    public String testPostJSON(@RequestBody UserDTO userDTO, HttpServletRequest httpServletRequest) {
        log.info("userDTO = {}", JSON.toJSONString(userDTO));
        printHeader(httpServletRequest);
        return "test3";
    }

    @PostMapping("test4")
    public String testPostFile(@RequestParam("fileName") MultipartFile[] multipartFiles
                , UserDTO userDTO
                , HttpServletRequest httpServletRequest) {
        log.info("userDTO = {}", JSON.toJSONString(userDTO));
        for (MultipartFile multipartFile : multipartFiles) {
            System.out.println("上传的文件名：" + multipartFile.getOriginalFilename());
        }
        printHeader(httpServletRequest);
        return "test4";
    }

    private void printHeader(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            StringBuilder sb = new StringBuilder();
            sb.append(headerName);
            sb.append(" : ");
            sb.append(httpServletRequest.getHeader(headerName));
            System.out.println(sb.toString());
        }
    }
}
