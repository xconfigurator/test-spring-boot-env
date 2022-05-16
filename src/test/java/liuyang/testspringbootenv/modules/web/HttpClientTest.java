package liuyang.testspringbootenv.modules.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * https://hc.apache.org/
 * httpclient4 依赖库原因，参见test-client （该工程基于5.x）
 * httpclient4 代码参考 test-client-httpclient4
 *
 * @author liuyang(wx)
 * @since 2021/12/27
 * @update 2022/5/16 copy from test-client-httpclient4
 *
 */
@Slf4j
public class HttpClientTest {

    @Test
    void testGet() throws IOException, ParseException {
        //String url = "http://www.baidu.com";
        String url = "http://www.sina.com/";// 实测https://www.sina.com也OK。
        //String url = "https://muyan-yootk.com/";// 调用方法参见HttpClientSSLTest
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        showResponse(response);

        response.close();
        httpClient.close();
    }

    // 5.x 和 4.x还是有区别的
    private void showResponse(CloseableHttpResponse response) throws IOException, ParseException {
        //log.info("header = {}", response.getHeaders());// 这样输出是打不全的
        /*
        log.info("header begin");
        for (Header header : response.getHeaders()) {
            log.info("header = {}", header);
        }
        log.info("header end");
        */
        log.info("body = {}", EntityUtils.toString(response.getEntity()));
        /*
        log.info("code = {}", response.getCode());
        log.info("version = {}", response.getVersion());
        */
    }
}
