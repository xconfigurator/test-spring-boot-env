package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.dto.ExcelVO;
import liuyang.testspringbootenv.modules.web.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频参考：
 * https://www.bilibili.com/video/BV17m4y1Q7gn?p=11&spm_id_from=333.880.my_history.page.click
 *
 * 文件上传
 * @author liuyang(wx)
 * @since 2022/1/20
 *
 * @update 2022/3/22 增加上传Excel并处理流程
 */
@RestController
@CrossOrigin
@RequestMapping("/upload")
@Slf4j
public class FileUploadController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/excel")
    public R doExcelUploadAndProcess(@RequestParam("file")MultipartFile file) {
        log.info("进入 doExcelUploadAndProcess");
        try {
            List<ExcelVO> list = excelService.list(file.getInputStream());
            // list
            list.stream().forEach(System.out::println);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error(e.getMessage());
        }
        log.info("退出 doExcelUploadAndProcess");
        return R.ok();
    }

}
