package liuyang.testspringbootenv.modules.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 日志拦截器
 *
 * @author suhj
 * @version 1.0 20200326
 * @modify liuyang 20220718 fix
 */
@Slf4j
public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        traceRequest(httpRequest, body);

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, body);

        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("******http request*****");
            log.debug("{} {}", request.getMethod(), request.getURI());

            HttpHeaders httpHeades = request.getHeaders();
            // httpHeads.entrySet()必须被使用。且其实现保证entrySet()返回不为null，故不需要对null进行处理。
            //if (null == httpHeades.entrySet()) return; // 20220718 liuyang add
            for (Map.Entry<String, List<String>> item : httpHeades.entrySet()) {
                log.debug("{}: {}", item.getKey(), item.getValue());
            }
            log.debug("{}", new String(body, StandardCharsets.UTF_8));
        }
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("******http response*****");
            log.debug("{} {}", response.getStatusCode(), response.getStatusText());
            HttpHeaders httpHeades = response.getHeaders();
            // httpHeads.entrySet()必须被使用。且其实现保证entrySet()返回不为null，故不需要对null进行处理。
            //if (null == httpHeades.entrySet()) return; // 20220718 liuyang add
            for (Map.Entry<String, List<String>> item : httpHeades.entrySet()) {
                log.debug("{}: {}", item.getKey(), item.getValue());
            }
            StringBuilder inputStringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
                String line = bufferedReader.readLine();
                while (line != null) {
                    inputStringBuilder.append(line);
                    inputStringBuilder.append('\n');
                    line = bufferedReader.readLine();
                }
            }
            log.debug("{}", inputStringBuilder.toString());
        }
    }
}
