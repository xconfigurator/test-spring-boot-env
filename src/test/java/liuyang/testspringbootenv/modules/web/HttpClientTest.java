package liuyang.testspringbootenv.modules.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * https://hc.apache.org/
 *
 * @author liuyang(wx)
 * @since 2021/12/27
 */
@Slf4j
public class HttpClientTest {

    // GET
    @Test
    void helloHttpClient() throws IOException, ParseException {
        String url = "http://www.baidu.com";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));

        // 消息头
        Header[] headers = response.getHeaders();
        for (Header header : headers) {
            log.info("头信息: name = {}, value = {}", header.getName(), header.getValue());
        }

        // 消息体
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity);
        log.info("消息体：{}", s);

        // 状态码
        log.info("状态码：{}", response.getCode());

        response.close();
        httpClient.close();
    }

    // POST 14:31
    // TODO

}
