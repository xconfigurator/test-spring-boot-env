package liuyang.testspringbootenv.modules.messaging.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import liuyang.testspringbootenv.modules.messaging.websocket.dto.JVMInfo;
import liuyang.testspringbootenv.modules.messaging.websocket.service.JVMInfoService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.TreeMap;

/**
 * 视频：
 * https://www.bilibili.com/video/BV1sW411r7Tz/?p=11&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 但视频里的写法貌似有问题！其实他自己的演示也是一开始订阅就能接收到定时任务发送的消息（其实是定时任务发送到指定topic的消息）。
 *
 * @author xconf
 * @since 2022/11/8
 */
@Deprecated // 根本不需要访问，客户端只需要订阅即可。 只是视频讲解里用了，这里仅做保留。
@Controller
@Slf4j
public class JVMInfoPushController {

    /*
    @Autowired
    private JVMInfoService jvmInfoService;
    */

    @MessageMapping("/jvminfo")
    public void jvmInfo() {
        log.debug("还是需要走这个流程的。");
    }
}
