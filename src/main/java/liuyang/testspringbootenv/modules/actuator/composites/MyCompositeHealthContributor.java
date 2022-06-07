package liuyang.testspringbootenv.modules.actuator.composites;

import liuyang.testspringbootenv.modules.actuator.deprecated.ThreadPoolTaskExecutorIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 组合指标监视器（非必须）
 *
 * 参考：https://blog.csdn.net/a20023930/article/details/110918477
 *
 * @author liuyang(wx)
 * @since 2022/4/13
 */
//@Component
public class MyCompositeHealthContributor implements CompositeHealthContributor {

    @Autowired
    ThreadPoolTaskExecutorIndicator threadPoolTaskExecutorIndicator;

    private Map<String, HealthContributor> healthContributors = new HashMap<>();

    @PostConstruct
    public void init() {
        healthContributors.put("ThreadPoolTaskExecutor", threadPoolTaskExecutorIndicator);
    }

    @Override
    public HealthContributor getContributor(String name) {
        return healthContributors.get(name);
    }

    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        List<NamedContributor<HealthContributor>> contributors = new ArrayList<>();
        healthContributors.forEach((name, contributor) -> {
            contributors.add(NamedContributor.of(name, contributor));
        });
        return contributors.iterator();
    }
}
