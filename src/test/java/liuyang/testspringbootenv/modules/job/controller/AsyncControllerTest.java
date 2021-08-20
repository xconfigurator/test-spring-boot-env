package liuyang.testspringbootenv.modules.job.controller;

import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyang
 * @scine 2021/7/5
 */
@Slf4j
public class AsyncControllerTest {
    RestTemplate restTemplate;

    @BeforeEach
    void init() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(1000);
        simpleClientHttpRequestFactory.setReadTimeout(1000);
        restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Test
    void testConnTimeout() {
        // org.springframework.web.client.ResourceAccessException: I/O error on GET request for "http://xxx.baidu.com": xxx.baidu.com; nested exception is java.net.UnknownHostException: xxx.baidu.com
        ResourceAccessException e = Assertions.assertThrows(ResourceAccessException.class, () -> {
            R forObject = restTemplate.getForObject("http://xxx.baidu.com", R.class);
        });
        log.error(e.getMessage(), e);
    }

    @Test
    void testSync() {
        // org.springframework.web.client.ResourceAccessException: I/O error on GET request for "http://localhost/async/sync": Read timed out;
        ResourceAccessException e = Assertions.assertThrows(ResourceAccessException.class, () -> {
            R forObject = restTemplate.getForObject("http://localhost/async/sync", R.class);
        });
        log.error(e.getMessage(), e);
    }

    @Test
    void testAsync() {
        // 正常
        R forObject = restTemplate.getForObject("http://localhost/async/async", R.class);
    }
}
