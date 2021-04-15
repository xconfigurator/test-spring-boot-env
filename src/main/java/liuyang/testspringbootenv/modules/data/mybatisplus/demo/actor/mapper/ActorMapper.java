package liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.mapper;

import liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.entity.Actor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuyang
 * @since 2021-04-13
 */
@Mapper
public interface ActorMapper extends BaseMapper<Actor> {

}
