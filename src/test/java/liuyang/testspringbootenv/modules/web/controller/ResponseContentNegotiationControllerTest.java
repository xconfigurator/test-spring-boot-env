package liuyang.testspringbootenv.modules.web.controller;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author xconf
 * @since 2023/12/30
 */
@Slf4j
public class ResponseContentNegotiationControllerTest {
    @Test
    void testObject2Yaml() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        UserDTO user = new UserDTO(IdUtil.randomUUID(), "刘洋", "foo", 12.3, new BigDecimal(123123));
        System.out.println(om.writeValueAsString(user));
        // 注：需要增加依赖
        /*
        <!-- 测试增加yaml格式数据能力 20231230 add -->
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-yaml</artifactId>
        </dependency>
         */
    }

}
