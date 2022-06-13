package liuyang.testspringbootenv.modules.cache.file01;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author liuyang(wx)
 * @since 2022/6/1
 */
@Slf4j
public class DataCacheUtilTest {

    String SYSTEM_CACHE_FILE_NAME = "local.data.cache";

    // 删
    @Test
    void testDataCache04() throws IOException {
        DataCacheUtil.fileInit(SYSTEM_CACHE_FILE_NAME);
        DataCacheUtil.getStaticMapCache().remove("k1");
        DataCacheUtil.dataCache(DataCacheUtil.getStaticMapCache(), SYSTEM_CACHE_FILE_NAME);
    }


    // 改
    @Test
    void testDataCache03() throws IOException {
        DataCacheUtil.fileInit(SYSTEM_CACHE_FILE_NAME);
        DataCacheUtil.getStaticMapCache().put("k1", "liu");
        DataCacheUtil.dataCache(DataCacheUtil.getStaticMapCache(), SYSTEM_CACHE_FILE_NAME);
    }

    // 增
    // 模拟生产运行中添加数据
    @Test
    void testDataCache02() throws IOException {
        DataCacheUtil.fileInit(SYSTEM_CACHE_FILE_NAME);
        DataCacheUtil.getStaticMapCache().put("k1", "foo");
        DataCacheUtil.getStaticMapCache().put("k2", "bar");
        DataCacheUtil.dataCache(DataCacheUtil.getStaticMapCache(), SYSTEM_CACHE_FILE_NAME);
    }

    // BaseRunner中run处初始加载缓存
    @Test
    void testDataCache01() throws IOException {
        // 从文件中读数据，如果文件存在则把数据存到HashMap中（getStaticMapCache），如果文件不存在则什么也不做并输出“系统第一次生产运行！”
        DataCacheUtil.fileInit(SYSTEM_CACHE_FILE_NAME);
        log.info("初始化数据：{}", DataCacheUtil.getStaticMapCache());
        DataCacheUtil.dataCache(DataCacheUtil.getStaticMapCache(), SYSTEM_CACHE_FILE_NAME);
    }
}
