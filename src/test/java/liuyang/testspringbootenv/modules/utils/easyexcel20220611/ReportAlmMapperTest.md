```java
package com.hbfec.fiberhome.mapper;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hbfec.fiberhome.bean.ReportAlm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author liuyang(wx)
 * @since 2022/6/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ReportAlmMapperTest {

    @Autowired
    private ReportAlmMapper reportAlmMapper;

    @Test
    public void testQuery() {
        List<ReportAlm> reportAlms = reportAlmMapper.selectList(null);
        reportAlms.stream().forEach(System.out::println);
    }

    @Test
    public void initReportAlarmData() {
        // TODO
    }

    @Test
    public void readAlarmDictionary() throws IOException {
        // 获取告警字典
        List<AIOpsAlarmDictionary> aiOpsAlarmDictionaries = readDictionary();
        log.info("字典大小：{}", aiOpsAlarmDictionaries.size());

        // 随机产生告警信息
        for (int i = 0; i < 10; ++i) {
            ReportAlm reportAlm = makeAlarmInfoRandom(aiOpsAlarmDictionaries);
            log.info("reportAlm = {}", reportAlm);
            reportAlmMapper.insert(reportAlm);
        }
    }


    /**
     * 按照智能运维告警字典表中信息随机产生一条告警数据
     * @param aiOpsAlarmDictionaries
     * @return
     */
    private ReportAlm makeAlarmInfoRandom(List<AIOpsAlarmDictionary> aiOpsAlarmDictionaries) {
        // 随机选一条告警类型
        Random random = new Random();
        int i = random.nextInt(aiOpsAlarmDictionaries.size());
        AIOpsAlarmDictionary aiOpsAlarmDictionary = aiOpsAlarmDictionaries.get(i);
        log.info("random i = {}, aiOpsAlarmDictionary = {}",i, aiOpsAlarmDictionary);

        ReportAlm reportAlm = new ReportAlm();
        reportAlm.setAid(666);// not null
        reportAlm.setRid(666);// not null
        reportAlm.setDaid(666);// not null
        reportAlm.setProperty(0);// not null
        reportAlm.setSeries(1);// aiops 要求
        reportAlm.setStyle(1);// aiops 要求
        reportAlm.setEquipid(1);// aiops 要求
        reportAlm.setSeverity(1);
        reportAlm.setStatus(2);
        reportAlm.setDatetime(new Date());
        reportAlm.setAlmnum(Long.parseLong(aiOpsAlarmDictionary.getAlarmCode()));// aiops 要求 告警编码
        reportAlm.setAlmcategory(aiOpsAlarmDictionary.getAlarmType());// aiops 要求 告警类型
        reportAlm.setAlmtext(aiOpsAlarmDictionary.getAlarmInfo());// aiops 要求 告警文本
        reportAlm.setAck(0);// not null
        reportAlm.setState(1);// not null
        reportAlm.setRepeatcount(0);// not null
        reportAlm.setRecoveruser(0);// not null
        reportAlm.setOnalmnum(2l);// not null
        reportAlm.setOnalmcategory("test");
        return reportAlm;
    }

    private List<AIOpsAlarmDictionary> readDictionary() throws IOException{
        String filePath = "E:\\project_202204_soph-devops-north-interface_docs\\告警知识库列表 (1).xls";// OK
        //String filePath = "G:\\告警知识库列表 (1).xlsx";// OK
        File file = new File(filePath);
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

        //list.stream().forEach(System.out::println);

        return list;
    }
}
```

