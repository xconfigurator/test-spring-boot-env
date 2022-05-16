package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.DateUtil;
import liuyang.testspringbootenv.common.utils.IdUtils;
import liuyang.testspringbootenv.modules.json.util.JsonUtil;
import liuyang.testspringbootenv.modules.web.vo.Department;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.Closeable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


/**
 * 说明：使用 Apache HttpClient 4.5.13
 *
 * @author liuyang(wx)
 * @since 2022/5/16
 */
@Slf4j
public class ValidatorControllerTest {
    private static final int CONNECT_TIMEOUT = 5000; // TCP握手时间
    private static final int SOCKET_TIMEOUT = 60000; // 请求响应时间

    // 看一下整体框架能不能用
    @Test
    void test00() {
        //String url = "https://www.baidu.com";
        String url = "http://localhost/valid";
        HttpGet httpGet = new HttpGet(url);
        try (
                CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        ){
            HttpEntity entity = closeableHttpResponse.getEntity();
            String entityStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);

            assertEquals("{\"msg\":\"hello Spring Boot Env test BeanValidator JSR 303/ JSR 349/ JSR 380\",\"code\":0}", entityStr);

            log.info("entityStr = {}", entityStr);
            EntityUtils.consume(entity);// 确保关闭流
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void test01() {
        // Long aLong = IdUtils.nextIdViaHutool();
        // System.out.println(aLong);

        // 值对象
        Department department = new Department();
        //department.setId(IdUtils.nextIdViaHutool());// 验证规则是@Null 所以传了就不对！
        department.setParent_id(IdUtils.nextIdViaHutool());
        department.setName("foo");
        department.setCreateTime(DateUtil.asLocalDateTime(new Date()));

        // JSON
        String jsonData = JsonUtil.toJSONString(department);
        log.info("jsonData = {}", jsonData);

        // HttpClient4
        String url = "http://localhost/valid/department";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");// 不设置就报错。因为默认类型是：Content type 'text/plain;charset=UTF-8'
        StringEntity jsonEntity = new StringEntity(jsonData, StandardCharsets.UTF_8);
        httpPost.setEntity(jsonEntity);

        try (
                CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
                ) {
            HttpEntity entity = closeableHttpResponse.getEntity();
            String entityStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            log.info("entityStr = {}", entityStr);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        
    }

}
