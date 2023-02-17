package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 视频参考
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=5&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 11:25
 * @author xconf
 * @since 2023/2/17
 */
@Slf4j
public class Guava02UnderscoreCamelTest {
    @Test
    void test() {
        Assertions.assertEquals("studentName"
                , CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "student_name"));
        Assertions.assertEquals("StudentName"
                , CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "student_name"));
        Assertions.assertEquals("student_name"
                , CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "studentName"));
        Assertions.assertEquals("student_name"
                , CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "StudentName"));
    }
}
