package liuyang.testspringbootenv.modules.web;

import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * get post exchange
 * docs:
 * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-resttemplate
 *
 * RestTemplate: The original Spring REST client with a synchronous, template method API.
 * WebClient: a non-blocking, reactive alternative that supports both synchronous and asynchronous as well as streaming scenarios.
 *
 * As of 5.0 the RestTemplate is in maintenance mode, with only minor requests for changes and bugs to be accepted going forward.
 * Please, consider using the WebClient which offers a more modern API and supports sync, async, and streaming scenarios.
 *
 * getForObject 获取请求对象
 * getForEntity 获取请求对象 + Http头等其他信息
 *
 * postForObject
 * postForEntity
 *
 * exchange
 *
 * @author liuyang(wx)
 * @since 2021/11/25
 */
@Slf4j
@SpringBootTest
public class RestTemplateTest {
    /*
    private static RestTemplate restTemplate;
    static {
        // 关于初始化: 找实现了ClientHttpRequestFactory接口的类，注册进RestTemplate即可。
        // 1. 底层使用java.net.HttpURLConnection
        restTemplate = new RestTemplate();
        // 2. 底层使用ApacheHttpComponents
        // restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());// 需要添加相关依赖。
        // 3. 底层使用Netty
        // 4. 底层使用OkHttp
    }
     */
    @Autowired
    private RestTemplate restTemplate;

    // Http协议状态码解析
    // 状态码
    // https://www.runoob.com/http/http-status-codes.html
    // https://httpstatus.com/
    @Test
    void httpStatusTest() {
        // 100 200 301 302 404 500
    }

    @Test
    void getForObjectTest() {
        String url = "http://localhost/hello/dto";
        Map<String, Long> paramMap = new HashMap<>();
        UserDTO forObject = restTemplate.getForObject(url, UserDTO.class, paramMap);
        log.info("forObject = {}", forObject);
    }

    @Test
    void getForEntityTest() {
        String url = "http://localhost/hello/dto";
        Map<String, Long> paramMap = new HashMap<>();
        ResponseEntity<UserDTO> forEntity = restTemplate.getForEntity(url, UserDTO.class, paramMap);
        // 可以拿到如下信息
        forEntity.getStatusCode();
        forEntity.getStatusCodeValue();
        forEntity.getHeaders();
        forEntity.getBody();
        log.info("forObject = {}", forEntity.getBody());
    }

    /**
     * 传参注意：
     * postForObject请求中，需要使用MultiValueMap（基本类型、对象类型传参均可）。
     */
    @Test
    void postForObjectTest() {
        String url = ""; // TODO
        MultiValueMap<String ,Object> paramMap = new LinkedMultiValueMap<>();
        UserDTO userDTO = restTemplate.postForObject(url, paramMap, UserDTO.class);
        log.info("postForObject = {}", userDTO);
    }

    /**
     * 注意：
     * postForEntity请求中，
     * 1. 需要使用MultiValueMap传参。
     * 2. 当服务端使用“@RequestBody”传参时（e.g. methodName(@RequestBody Map param)），必须使用HttpEntity（本例演示） 。
     */
    @Test
    void postForEntityTest() {
        String url = ""; // TODO
        // 头(MultiValueMap<String, String>)
        HttpHeaders httpHeaders = new HttpHeaders();// HttpHeaders是一个MultiValueMap
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// application/json
        // 数据(T)
        Map<String, Long> body = new HashMap<>();
        body.put("foo", 123l);
        // HttpEntity
        HttpEntity<Map<String, Long>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.postForEntity(url, httpEntity, UserDTO.class);
        userDTOResponseEntity.getStatusCode();
        userDTOResponseEntity.getStatusCodeValue();
        userDTOResponseEntity.getHeaders();
        userDTOResponseEntity.getBody();
        log.info("postForEntity = {}", userDTOResponseEntity.getBody());
    }

    /**
     * 既可以发送GET请求也可以发送POST请求。
     */
    @Test
    void exchangeTest() {
        String url = ""; // TODO
        // 头(MultiValueMap<String, String>)
        HttpHeaders httpHeaders = new HttpHeaders();// HttpHeaders是一个MultiValueMap
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// application/json
        // 数据(T)
        Map<String, Long> body = new HashMap<>();
        body.put("foo", 123l);
        // HttpEntity
        HttpEntity<Map<String, Long>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<UserDTO> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, UserDTO.class);
        log.info("exchange = {}", exchange.getBody());
    }
}