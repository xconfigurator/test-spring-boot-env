package liuyang.testspringbootenv.modules.cache.file02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 将内存中的一个map做缓存，并提供一定的持久化能力。
 *
 * @author liuyang(wx)
 * @since 2022/6/8
 */
@ConditionalOnProperty(prefix = "enable", name = "modules.cache.filecache", havingValue = "true")
@Component
public class FileCache implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(FileCache.class);

    @Value("${filecache.localcachefilename}")
    private String localCacheFileName;

    // 核心存储容器
    private static Map<String, String> mapCache = new HashMap<>();// 本使用场景不需要用ConcurrentHashMap，逻辑上各键隔离，就是要利用HashMap的无锁高性能特性。

    @PostConstruct
    private void init() {
        load();
    }

    public void put(String key, String value) {
        mapCache.put(key, value);
    }

    public String get(String key) {
        return mapCache.get(key);
    }

    public void remove(String key) {
        mapCache.remove(key);
    }

    // 读文件
    public void load() {
        File file = getCacheFile();
        if (null != file&& file.exists() && file.isFile()) {
            try (
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(isr);
                    ) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        if (!StringUtils.isEmpty(line)) {
                            String[] kv = line.split("=");
                            mapCache.put(kv[0], kv[1]);
                        }
                    }
            } catch (Exception e) {
                log.error("缓存文件 初始化数据异常");
                log.error(e.getMessage(), e);
            }
        } else {
            log.info("系统第一次生产运行！xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }
    }

    // 写文件
    public void flush() {
        File file = getCacheFile();
        // 若缓存文件不存在则创建
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    log.info("缓存文件创建成功 ,{}", file.getCanonicalPath());
                }
            } catch (Exception e) {
                log.error("缓存文件不存在，并且创建失败");
                log.error(e.getMessage(), e);
            }
        }
        // 写
        try (
                FileOutputStream fos = new FileOutputStream(file, false);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw);
                ) {
            for (Map.Entry<String, String> entry : mapCache.entrySet()) {
                bw.write(entry.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //throw new IOException(e);
        }
    }

    // 获取本地缓存文件引用
    private File getCacheFile() {
        // 获得根路径
        String baseUrl = null;
        File directory = new File("");
        try {
            baseUrl = directory.getCanonicalPath();
            log.debug("baseUrl = {}", baseUrl);
        } catch (IOException e) {
            log.error("获取项目根目录失败：");
            log.error(e.getMessage(), e);
            return null;
        }

        // 文件
        File file = new File(baseUrl + File.separator + localCacheFileName);
        try {
            log.debug("正在访问文件：{}", file.getCanonicalPath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return file;
    }

    @Override
    public void destroy() throws Exception {
        // 保证正常容器关闭的时候执行持久化操作。
        flush();
    }
}
