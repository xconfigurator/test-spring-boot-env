package liuyang.testspringbootenv.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author liuyang(wx)
 * @since 2022/5/25
 */
@Data
@Component
@PropertySource("classpath:config/system-config.properties")
public class SystemConfig {

    @Value("${foo}")
    private String foo;
}
