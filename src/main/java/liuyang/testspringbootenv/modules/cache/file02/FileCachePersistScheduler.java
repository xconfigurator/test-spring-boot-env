package liuyang.testspringbootenv.modules.cache.file02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 用于缓存本模块的上报信息
 *
 * 持久化路径：
 * 1. 开放flush方法，可以在业务逻辑处调用flush完成写文件动作。
 * 2. DisposableBean的destroy方法调用flush方法，确保在容器正常关闭前完成持久化操作。
 * 3. 配合定时器可完成定时flush操作。
 *
 * @author liuyang(wx)
 * @since 2022/6/8
 */
@ConditionalOnProperty(prefix = "enable", name = "modules.cache.filecache", havingValue = "true")
@ConditionalOnBean(FileCache.class)
@Configuration
@EnableScheduling
@Slf4j
public class FileCachePersistScheduler {

    @Autowired
    private FileCache fileCache;

    @Scheduled(cron = "0/2 * * * * ?")
    public void persistCache() {
        fileCache.flush();
        log.info("fileCache.flush()");
    }
}
