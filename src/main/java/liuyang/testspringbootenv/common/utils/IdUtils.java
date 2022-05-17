package liuyang.testspringbootenv.common.utils;

import java.util.UUID;

/**
 * @author liuyang
 * @scine 2021/9/27
 *        2022/5/16 增加基于hutool的
 */
public class IdUtils {

    // JDK UUID
    public static String nextTaskId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // Snowflake - Hutool实现
    // 关于ID，如果使用雪花算法，数据库中的对应字段使用BIGINT
    // 生成整数为19位，Java应使用Long， MySQL对应字段应使用BIGINT
    // MySQL https://dev.mysql.com/doc/refman/5.7/en/integer-types.html
    // SQL Server 2019 https://docs.microsoft.com/zh-cn/sql/t-sql/data-types/int-bigint-smallint-and-tinyint-transact-sql?view=sql-server-ver15
    private static cn.hutool.core.lang.Snowflake snowflake;
    private static long workerId = 1;      // 5位：1 - 31
    private static long datacenterId = 1;  // 5位：1 - 31
    static {
        snowflake = cn.hutool.core.util.IdUtil.createSnowflake(workerId, datacenterId);
    }
    public static Long nextIdViaHutool() {
        return snowflake.nextId();
    }
}
