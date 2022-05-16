package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 1. 简单类型使用stored
 *          .sorted()
 * 2. sorted传入Comparator
 *          .sorted((a, b) -> a.compareTo(b))
 * 3. 倒序：
 *          Comparator.reverseOrder()
 * 4. 自定义排序：
 *          Comparator.comparaing(
 *                  user -> user.getUsername(),
 *                  (a, b) -> a.compareTo(b)
 *               )
 *    中文：
 *    Collator simplefiedChinese = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);
 */
@Slf4j
//@ExtendWith(SpringExtension.class)
public class _02_07_SortedTests {

    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13000000003").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    // 01:51
    @Test
    public void givenCollections_withoutStream_thenSort() {
        List<String> list = Arrays.asList("One", "Abc", "BCD");
        log.debug("未排序：{}", list);
        assertEquals("One", list.get(0));
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        log.debug("排序后：{}", list);
        assertEquals("Abc", list.get(0));
    }

    //
    @Test
    public void givenCollections_withStream_thenSort() {
        List<String> list = Arrays.asList("One", "Abc", "BCD");
        // 1. stored
        // 等价于.stored(Comparator.naturalOrder())
        List<String> sortedList = list.stream().sorted().collect(toList());
        log.debug("sortedList =  {}", sortedList);
        assertEquals("Abc", sortedList.get(0));
        // 2. 传入Comparator
        // 下面的写法等价于传递Comparator.naturalOrder();
        List<String> sortedList2 = list.stream().sorted((a, b) -> a.compareTo(b)).collect(toList());
        log.debug("sortedList2 = {}", sortedList2);
        assertEquals("Abc", sortedList2.get(0));
        // 3. 倒序 Comparator.reverseOrder();
        // 联想还有：Comparator.naturalOrder();
        List<String> sortedList3 = list.stream().sorted(Comparator.reverseOrder()).collect(toList());
        log.debug("sortedList3 = {}", sortedList3);
        assertEquals("One", sortedList3.get(0));
        // 4. 自定义排序（比较对象）
        List<User> sortedList4 = userList.stream().sorted(Comparator.comparing(
                user -> user.getUsername(),
                (a, b) -> b.compareTo(a)
                )).collect(toList());
        log.debug("strtedList4 = {}", sortedList4);

        // 关于中文
        Collator simplefiedChinese = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);
        List<User> sortedList5 = userList.stream().sorted(Comparator.comparing(
                user -> user.getUsername(),
                simplefiedChinese
                //simplefiedChinese.reversed()
        )).collect(toList());
        log.debug("sortedList5 = {}", sortedList5);
    }
}
