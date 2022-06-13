package liuyang.testspringbootenv.modules.cache.file01;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class DataCacheUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCacheUtil.class);

    private static Map<String,String> mapCache = new HashMap<>();

    private static String baseUrl;

    static {
        File directory = new File("");
        try{
            baseUrl = directory.getCanonicalPath();
        }catch (IOException e){
            LOGGER.error("获取项目跟目录失败：{}", e.getMessage());
        }
    }

    public static Map<String,String> getStaticMapCache(){
        return mapCache;
    }

    /**
     *  创建文件，写入缓存数据
     */
    public static void dataCache(Map<String,String> map,String fileName) throws IOException {
        //创建文件
        if(!map.isEmpty()){
            File file = new File(baseUrl+File.separator+fileName);
            if(!file.exists()){
                try {
                    if(file.createNewFile()){
                        LOGGER.info("缓存文件创建成功，{}", fileName);
                    }
                } catch (IOException e) {
                   LOGGER.error("缓存文件不存在，并且创建失败：{}", fileName);
                }
            }
            try (
                    FileOutputStream fos = new FileOutputStream(file,false);
                    OutputStreamWriter out = new OutputStreamWriter(fos);
                    BufferedWriter bw = new BufferedWriter(out)
                    ){

                for(Object key : map.entrySet()){
                    bw.write(key+"");
                    bw.newLine();
                }

            } catch (IOException e){
                LOGGER.error("写入缓存文件失败");
                throw new IOException(e);
            }
        }
    }

    /**
     * 初始化数据
     */
    public static void fileInit(String fileName){
        //读取文件，赋值
        File file = new File(baseUrl+File.separator+fileName);
        if (file.isFile() && file.exists()) {
            try(
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader read = new InputStreamReader(fis,StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(read)
                    ) {

                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    if (!StringUtils.isEmpty(tempString)) {
                        String[] temp = tempString.split("=");
                        mapCache.put(temp[0], temp[1]);
                    }
                }
            }catch (IOException e){
                LOGGER.error("缓存文件  初始化数据异常:{}", fileName);
            }
        }else{
            LOGGER.info("系统第一次生产运行！");
        }
    }

    private DataCacheUtil(){

    }
}
