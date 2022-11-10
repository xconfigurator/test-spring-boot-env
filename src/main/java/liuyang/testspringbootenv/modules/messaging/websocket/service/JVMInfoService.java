package liuyang.testspringbootenv.modules.messaging.websocket.service;

import com.alibaba.fastjson.JSONObject;
import liuyang.testspringbootenv.modules.messaging.websocket.dto.JVMInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author xconf
 * @since 2022/11/8
 */
@Service
@Slf4j
public class JVMInfoService {

    private final String DESTINATION_JVM_INFO = "/topic/jvminfo";

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    //@Scheduled(cron = "0/1 * * * * ?")
    @Scheduled(fixedRate = 1000l)
    public void jvmInfo() {
        JVMInfo jvmInfo = new JVMInfo();
        jvmInfo.setAvailableProcessors(Runtime.getRuntime().availableProcessors());
        jvmInfo.setMaxMemory(Runtime.getRuntime().maxMemory());
        jvmInfo.setFreeMemory(Runtime.getRuntime().freeMemory());
        log.debug("jvminfo {}", jvmInfo);
        //simpMessagingTemplate.convertAndSend(DESTINATION_JVM_INFO, jvmInfo);
        simpMessagingTemplate.convertAndSend(DESTINATION_JVM_INFO, JSONObject.toJSONString(jvmInfo));
    }
}
