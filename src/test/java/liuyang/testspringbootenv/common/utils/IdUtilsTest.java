package liuyang.testspringbootenv.common.utils;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author liuyang(wx)
 * @since 2022/5/17
 */
public class IdUtilsTest {
    @Test
    void testNextTaskId() {
        System.out.println(IdUtils.nextTaskId());
    }

    @Test
    void testNextIdViaHutool() {
        // 生成整数为19位，Java应使用Long， MySQL对应字段应使用BIGINT
        // MySQL https://dev.mysql.com/doc/refman/5.7/en/integer-types.html
        // SQL Server 2019 https://docs.microsoft.com/zh-cn/sql/t-sql/data-types/int-bigint-smallint-and-tinyint-transact-sql?view=sql-server-ver15
        System.out.println("Hutool实现：\t\t\t" + IdUtils.nextIdViaHutool());

        // MyBatis-Plus的实现
        System.out.println("MyBatis-Plus实现：\t" + IdUtils.nextId());
        System.out.println("MyBatis-Plus实现：\t" + IdUtils.nexUUID());
    }
}
