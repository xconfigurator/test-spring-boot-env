package liuyang.testspringbootenv.modules.utils.apachecommons2023;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author xconf
 * @since 2023/2/16
 */
@Slf4j
public class ApacheCommonsIOTest {
    @Test
    void testReadFile() throws IOException {
        final String filePath = "C:\\Users\\xconf\\workspaces\\workspace_idea_u\\test-spring-boot-env\\src\\main\\resources\\application-dev-workstation.yml";

        //文件名
        log.info("=====================================================================================");
        log.info("文件名：{}", FilenameUtils.getBaseName(filePath));
        log.info("扩展名：{}", FilenameUtils.getExtension(filePath));

        // 读成一个字符串
        //final String s = FileUtils.readFileToString(new File(filePath), Charset.forName("UTF-8"));
        final String s = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        log.info("{}", s);

        // 读成一个List<String>
        log.info("=====================================================================================");
        final List<String> strings = FileUtils.readLines(new File(filePath), StandardCharsets.UTF_8);
        strings.stream().forEach(l -> System.out.println(l));
    }
}
