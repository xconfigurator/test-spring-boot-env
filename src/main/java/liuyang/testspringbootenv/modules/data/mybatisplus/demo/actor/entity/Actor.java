package liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuyang
 * @since 2021-04-13
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Actor对象", description="")
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "actor_id", type = IdType.AUTO)
    private Integer actorId;

    private String firstName;

    private String lastName;

    private LocalDateTime lastUpdate;


}
