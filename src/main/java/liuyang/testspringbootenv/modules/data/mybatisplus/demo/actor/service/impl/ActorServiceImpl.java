package liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.service.impl;

import liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.entity.Actor;
import liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.mapper.ActorMapper;
import liuyang.testspringbootenv.modules.data.mybatisplus.demo.actor.service.ActorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyang
 * @since 2021-04-13
 */
// @Service
public class ActorServiceImpl extends ServiceImpl<ActorMapper, Actor> implements ActorService {

}
