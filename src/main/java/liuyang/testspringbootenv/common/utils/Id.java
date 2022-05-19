package liuyang.testspringbootenv.common.utils;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.UUID;

/**
 * 【雪花算法】输出结构
 * long型
 * 1bit位，符号，表示正数。0
 * 41bit时间戳。毫秒级。存储的是时间戳的差值（当前时间戳 - 开始时间戳），结果约等于69.73年。
 * 10bit机器ID。其中5bit数据中心，5bit机器ID，可以部署在1024个节点。
 * 12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生4096个ID）。
 *
 * @author liuyang
 * @scine 2021/9/27
 *        2022/5/16 增加hutool的实现         参考test-mbp项目想干测试用例
 *        2022/5/18 增加MyBatis-Plus的实现   参考test-mbp项目相关测试用例
 *        2022/5/19 IdUtils更名为Id。其实改成Ids更符合队形。
 */
public class Id {

    // JDK UUID
    public static String nextTaskId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // Snowflake - MyBatis-Plus begin
    private static IdentifierGenerator identifierGenerator;
    static {
        identifierGenerator = new DefaultIdentifierGenerator();// 另外一个实现是：ImadcnIdentifierGenerator
    }
    public static Long nextId() {
        return Long.valueOf(identifierGenerator.nextId(null).longValue());// Long类型 19位 见DefaultIdentifierGenerator
    }
    public static String nexUUID() {
        return identifierGenerator.nextUUID(null);
    }
    // Snowflake - MyBatis-Plus end

    // Snowflake - Hutool实现 begin
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
    // Snowflake - Hutool实现 end
}
