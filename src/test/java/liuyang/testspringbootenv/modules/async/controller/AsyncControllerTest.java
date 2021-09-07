package liuyang.testspringbootenv.modules.async.controller;

import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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

    @Test
    void testRestTemplate() {
        R forObject = restTemplate.getForObject("http://www.baidu.com", R.class);
        log.info("R = {}", forObject);
    }

    @Test
    void testHttpClientSinceJDK9() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).GET().build();
        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        log.info("resp.headers() = {}", resp.headers());
        log.info("resp.body() = {}", resp.body());
        log.info("resp = {}", resp);
    }
}
