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

    // Hutool Snowflake
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
