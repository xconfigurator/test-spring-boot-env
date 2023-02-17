package liuyang.testspringbootenv.modules.utils.easyexcel20220611;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import liuyang.testspringbootenv.modules.utils.easyexcel20220611.vo.AIOpsAlarmDictionary;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang(wx)
 * @since 2022/6/10
 */
@Slf4j
public class EasyExcelTest {

    // 注意：AIOpsAlarmDictionary 不能写在这个测试用例中。 20220611 实测 写到这里无法实例化。
    // class AIOpsAlarmDictionary {}
    // public class AIOpsAlarmDictionary {}

    // 就是读一个
    @Test
    void testRead() throws IOException {
        //String filePath = "E:\\project_202204_soph-devops-north-interface_docs\\告警知识库列表.xls";// OK
        //String filePath = "G:\\告警知识库列表 (1).xlsx";// OK
        //File file = new File(filePath);
        File file = ResourceUtils.getFile("classpath:data/告警知识库列表.xls");// 20220808
        //log.info("file = {}", file.getCanonicalPath());
        FileInputStream fis = new FileInputStream(file);

        List<AIOpsAlarmDictionary> list = new ArrayList<>();

        EasyExcel.read(fis).head(AIOpsAlarmDictionary.class).sheet().registerReadListener(new AnalysisEventListener<AIOpsAlarmDictionary>() {
            @Override
            public void invoke(AIOpsAlarmDictionary dictionary, AnalysisContext analysisContext) {
                list.add(dictionary);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                log.info("解析完毕");
            }
        }).doRead();

        fis.close();

        list.stream().forEach(System.out::println);

        // TODO
        /*
        @Override
    public List<ExcelVO> list(InputStream in) throws IOException {
        List<ExcelVO> list = new ArrayList<>();
        EasyExcel.read(in).head(ExcelVO.class).sheet().registerReadListener(new AnalysisEventListener<ExcelVO>() {
            @Override
            public void invoke(ExcelVO excelVO, AnalysisContext analysisContext) {
                list.add(excelVO);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                log.info("解析完毕");
            }
        }).doRead();
        if (in != null) {
            in.close();
        }
        return list;
    }
         */
    }
}
