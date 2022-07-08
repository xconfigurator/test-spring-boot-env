package liuyang.testspringbootenv.modules.cache.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.cache.service.FooCacheService;
import liuyang.testspringbootenv.modules.cache.vo.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang(wx)
 * @since 2022/7/8
 */
@RestController
@RequestMapping("cache")
@Slf4j
public class FooCacheController {

    @Autowired
    private FooCacheService fooCacheService;

    @GetMapping("cacheable")
    public R testCacheable(Integer id) {
        return R.ok().put("data", fooCacheService.getBookById(id));
    }

    @GetMapping("cacheput")
    public R testCachePut(Integer id, String name) {
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        return R.ok().put("data", fooCacheService.updateBookById(book));
    }

    @GetMapping("cacheevict")
    public R testCacheEvict(Integer id) {
        fooCacheService.deleteBookById(id);
        return R.ok();
    }
}

