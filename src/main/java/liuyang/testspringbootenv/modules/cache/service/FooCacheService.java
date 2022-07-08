package liuyang.testspringbootenv.modules.cache.service;

import liuyang.testspringbootenv.modules.cache.vo.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author liuyang(wx)
 * @since 2022/7/8
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "book_cache")
public class FooCacheService {

    @Cacheable
    public Book getBookById(Integer id) {
        log.info(" @Cacheable ");
        Book book = new Book();
        book.setId(id);
        book.setName("三国演义");
        book.setAuthor("罗贯中");
        return book;
    }

    @CachePut(key = "#book.id")
    public Book updateBookById(Book book) {
        log.info(" @CachePut ");
        book.setName(book.getName());
        return book;
    }

    @CacheEvict(key = "#id")
    public void deleteBookById(Integer id) {
        log.info(" @CacheEvict ");
    }
}
