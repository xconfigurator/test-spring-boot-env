package liuyang.testspringbootenv.modules.web.service;

import liuyang.testspringbootenv.modules.web.dto.ExcelVO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author liuyang(wx)
 * @since 2022/3/22
 */
public interface ExcelService {
    public List<ExcelVO> list(InputStream in) throws IOException;
}
