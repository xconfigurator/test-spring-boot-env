package liuyang.testspringbootenv.modules.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 读CSV
 * https://commons.apache.org/proper/commons-csv/user-guide.html
 *
 * @author liuyang(wx)
 * @since 2022/8/8
 */
@Slf4j
public class CommonsCsvTest {

    @Test
    void testReadCsvWithHeader202208081712() {
        
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
