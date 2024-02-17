package liuyang.testspringbootenv.modules.web.controller;

import cn.hutool.core.util.IdUtil;
import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 内容协商
 * 参考文档： https://www.yuque.com/atguigu/springboot/vgzmgh#iZojv
 * 参考视频：https://www.bilibili.com/video/BV1Es4y1q7Bf/?p=34&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * 【方法】
 * 1. 基于请求头的内容协商（默认开启）
 *      accept头带相关信息
 *      application/json, application/xml, text/xml,
 * 2. 基于请求参数内容协商（需要手动开启）
 *      使用不同的参数加以区分
 *      GET /cn/user?format=json
 *      GET /cn/user?format=xml
 *      根据协商参数，优先返回json数据类型。
 *
 * 【依赖】
 * json:    jackson(默认导入的)
 * xml:     jackson-dataformat-xml
 *          需要在值对象上添加@JacksonXmlRootElement
 *
 * @author liuyang(wx)
 * @since 2022/1/21
 *        2022/12/30
 */
@Slf4j
@RestController
@RequestMapping("/cn")
public class ResponseContentNegotiationController {
    /**
     * http://localhost/cn/user?format=json
     * http://localhost/cn/user?format=xml
     * http://localhost/cn/user?format=yaml
     */
    @GetMapping("/user")
    public UserDTO user() {
        UserDTO user = new UserDTO(IdUtil.randomUUID(), "刘洋", "foo", 12.3, new BigDecimal(123123));
        return user;
    }


}
