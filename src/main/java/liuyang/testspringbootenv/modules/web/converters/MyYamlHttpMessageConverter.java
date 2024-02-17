package liuyang.testspringbootenv.modules.web.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 视频参考：https://www.bilibili.com/video/BV1Es4y1q7Bf?p=37&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * 1. 引入干活的人依赖的包
 *      jackson-dataformat-yaml
 * 2. 编写配置文件
 *      spring.mvc.contentnegotiation.media-types.yaml=text/yaml
 * 3. 编写转换器
 *      MyYamlHttpMessageConverter
 * 4. 注册进Spring MVC:
 *      SpringWebMvcConfig
 *
 * @author xconf
 * @since 2023/12/30
 */
@Slf4j
public class MyYamlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private ObjectMapper objectMapper;

    public MyYamlHttpMessageConverter() {
        // 告知支持的媒体类型
        // 需要与配置文件中的相对应 spring.mvc.contentnegotiation.media-types.yaml=text/yaml
        super(new MediaType("text", "yaml", Charset.forName("UTF-8")));

        // 初始化干活的人
        YAMLFactory yamlFactory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        this.objectMapper = new ObjectMapper(yamlFactory);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;// 示意，只要是对象类型都转
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        // 配合@RequestBody
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // 配合@ResponseBody
        try(OutputStream os = outputMessage.getBody()) {
            this.objectMapper.writeValue(os, o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
