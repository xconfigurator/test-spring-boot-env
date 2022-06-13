package liuyang.testspringbootenv.modules.runner;

import liuyang.testspringbootenv.modules.cache.file02.FileCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author liuyang(wx)
 * @since 2022/6/11
 */
@ConditionalOnProperty(prefix = "enable", name = "modules.cache.filecache", havingValue = "true")
@ConditionalOnBean(FileCache.class)
@Component
public class FileCacheRunner implements ApplicationRunner {

    @Autowired
    private FileCache fileCache;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileCache.put("hello", "world");
    }
}
