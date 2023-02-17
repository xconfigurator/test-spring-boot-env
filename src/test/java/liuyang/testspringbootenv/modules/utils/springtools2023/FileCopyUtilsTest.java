package liuyang.testspringbootenv.modules.utils.springtools2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=10&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 18:47
 *
 * @author xconf
 * @since 2023/2/17
 */
@Slf4j
public class FileCopyUtilsTest {

    /**
     * 使用类路径
     */
    @Test
    void testClassPath() {
        String srcUnderClassPath = "application-dev-workstation.yml";
        String dest = "d:/application-dev-workstation-" + System.currentTimeMillis() + ".yml";
        try (
                InputStream is = new EncodedResource(new ClassPathResource(srcUnderClassPath), StandardCharsets.UTF_8).getInputStream();
                OutputStream os = new FileOutputStream(dest);
                )
        {
            FileCopyUtils.copy(is, os);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 使用绝对路径
     * @throws IOException
     */
    @Test
    void test() throws IOException {
        String src = "C:\\Users\\xconf\\workspaces\\workspace_idea_u\\test-spring-boot-env\\src\\main\\resources\\application-dev-workstation.yml";
        String dest = "d:/application-dev-workstation-" + System.currentTimeMillis() + ".yml";
        FileCopyUtils.copy(new File(src), new File(dest));
    }
}
