package liuyang.testspringbootenv.modules.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 只是一个传值示例, 作为ApplicationEvent的source
 * 实际使用应根据场景来传source还是值对象。但无论如何，Event就是一个广义值对象的携带者。
 *
 * @author liuyang
 * @scine 2021/7/29
 */
@AllArgsConstructor
@Data
@ToString
public class LiuyangEventDTO {
    private String value;
}
