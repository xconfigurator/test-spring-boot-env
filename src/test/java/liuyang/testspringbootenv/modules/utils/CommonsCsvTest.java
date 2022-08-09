package liuyang.testspringbootenv.modules.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * 读CSV
 * https://commons.apache.org/proper/commons-csv/user-guide.html
 *
 * @author liuyang(wx)
 * @since 2022/8/8
 */
@Slf4j
public class CommonsCsvTest {

    // 带标题
    @Test
    void testReadCsvWithHeaderAndBuildDictionary202208090855() throws IOException {
        final String FILE_RESOURCE_LOCATION = "classpath:data/fiberhome-alarmdic.csv";

        // step1 读文件
        // 设置列名，但按照列位置读取，以防现场更改csv文件的列名称。
        // 跟邹明沟通，现场配置文件需要保持标题。
        // 实测1：支持把csv文件的列名称命名为中文。但注意需要注意标题行的逗号是半角（csv格式规定）,
        // 实测2：就算现场把第一行改成中文并且使用了全角字符，也不会少记录。
        // 实测3：第一行随便写啥都行，但不能没有第一行。
        // 小结：编码使用了setHeader，csv中的首行内容就不重要了。
        final String COLUMN_01 = "AlmCategory";
        final String COLUMN_02 = "AlarmNum";
        final String COLUMN_03 = "AlarmCodeForFiberhome";
        final int IDX_OF_COLUMN_01 = 0;
        final int IDX_OF_COLUMN_02 = 1;
        final int IDX_OF_COLUMN_03 = 2;

        File file = ResourceUtils.getFile(FILE_RESOURCE_LOCATION);
        Reader reader = new FileReader(file);
        CSVParser parser = CSVFormat.Builder
                .create(CSVFormat.RFC4180)
                .setHeader(COLUMN_01, COLUMN_02, COLUMN_03)
                .setSkipHeaderRecord(true)
                .setIgnoreSurroundingSpaces(true)
                .build()
                .parse(reader);

        log.debug("headers = {}", parser.getHeaderNames());
        /*
        for (CSVRecord record : parser) {
            log.info("record = {}", record);
            log.info("{}, {}, {}", record.get(COLUMN_01), record.get(COLUMN_02), record.get(COLUMN_03));
        }
         */

        // setp2 拼字典
        Map<String, Integer> dictionary = new HashMap<>();// 需要全局单例
        // 1. 单条记录出现异常就只影响
        // 2. 所有记录都格式错误那就典表为不包含任何内容的空容器。
        for (CSVRecord record : parser) {
            try {
                StringBuilder key = new StringBuilder();
                key.append(record.get(IDX_OF_COLUMN_01));
                key.append(Integer.parseInt(record.get(IDX_OF_COLUMN_02)));
                Integer value = Integer.valueOf(record.get(IDX_OF_COLUMN_03));
                dictionary.put(key.toString(), value);
            } catch (NumberFormatException e) {
                log.error("加载典表时出现异常, CSV中第二列和第三列必须是整型, 请核对记录 = {} ", record);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.error("加载典表时出现异常, 请核对记录 = {}", record);
            }
        }
        log.info("dictionary = {}", dictionary);
        log.info("dictionary 有效记录数 = {}", dictionary.size());

        // setp3 释放资源
        parser.close();
        reader.close();
    }


    // 带标题的CSV文件读取
    @Test
    void testReadCsvWithHeader202208081712() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/hello.header.csv");
        Reader reader = new FileReader(file);
        CSVParser parser = CSVFormat.Builder
                .create(CSVFormat.RFC4180)
                .setHeader("Type", "AlarmCode", "FiberHomeAlarmCode")
                .setSkipHeaderRecord(true)
                .setIgnoreSurroundingSpaces(true)
                .build()
                .parse(reader);
        for (CSVRecord record : parser) {
            log.info("{}, {}, {}", record.get("Type"), record.get("AlarmCode"), record.get("FiberHomeAlarmCode"));
        }
        parser.close();
        reader.close();
    }

    // 根据源码“猜”出来的写法
    @Test
    void testReadCsv202208081645() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/hello.csv");
        Reader reader = new FileReader(file);
        CSVParser parser = CSVFormat.Builder.create(CSVFormat.RFC4180).setHeader("ID", "CustomerNo", "Name").build().parse(reader);
        for (CSVRecord record : parser) {
            String id = record.get("ID");
            String customerNo = record.get("CustomerNo");
            String name = record.get("Name");
            log.info("{}, {}, {}", id, customerNo, name);
        }
        parser.close();
        reader.close();
    }

    // 根据官方文档写的。不过api已经被标注未废弃。
    @Test
    void testReadCsv202208081644() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/hello.csv");
        Reader reader = new FileReader(file);
        CSVParser parser = CSVFormat.RFC4180.withHeader("ID", "CustomerNo", "Name").parse(reader);
        for (CSVRecord record : parser) {
            String id = record.get("ID");
            String customerNo = record.get("CustomerNo");
            String name = record.get("Name");
            log.info("{}, {}, {}", id, customerNo, name);
        }
        parser.close();
        reader.close();
    }
}
