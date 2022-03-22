package liuyang.testspringbootenv.modules.web.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import liuyang.testspringbootenv.modules.web.dto.ExcelVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang(wx)
 * @since 2022/3/22
 */
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService{
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
}
