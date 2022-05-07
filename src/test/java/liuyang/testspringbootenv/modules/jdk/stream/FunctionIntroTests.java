package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// chap01
@Slf4j
public class FunctionIntroTests {

    @AllArgsConstructor
    @Getter
    static class Book {
        private final String title;
        private final String author;
        private final Integer pages;
    }

    @Test
    public void givenListOfBooks_thenExplainTheAdvantageOfFunction() {
        String authorA = "张三";
        String authorB = "李四";
        String authorC = "王五";
        String authorD = "前朝太监";
        List<Book> books = Arrays.asList(
                new Book("C++编程", authorA, 1216),
                new Book("C#编程", authorA, 365),
                new Book("Java编程", authorB, 223),
                new Book("Ruby编程", authorB, 554),
                new Book("21天精通Python", authorB, 607),
                new Book("21天精通Go", authorC, 352),
                new Book("葵花宝典", authorD, 1200),
                new Book("编程圣经", authorA, 320)
        );
        List<Book> booksFiltered = new ArrayList<>();
        for (Book book : books){
            if (! "葵花宝典".equals(book.getTitle())) {
                booksFiltered.add(book);
            }
        }
        booksFiltered.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getPages().compareTo(o1.getPages());
            }
        });
        for (int i=0; i<3; i++) {
            System.out.println(booksFiltered.get(i).getAuthor());
        }
        books.stream()
                .filter(b -> ! "葵花宝典".equals(b.getTitle()))
                .sorted((b1, b2) -> b2.getPages().compareTo(b1.getPages()))
                .limit(3)
                .map(Book::getAuthor)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void givenTwoFunctions_thenComposeThemInNewFunction() {

    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingAnd() {

    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingOr() {

    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingCompose() {

    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingAndThen() {

    }

    // 1-3 02:58左右
    // 1-3 06左右 - 09:59 讲得Lambda表达式需要听一听。（静态方法引用、参数方法引用、实例方法引用、构造器引用。例子见MethodReferenceTests.java）
    @Test
    public void givenFunctionalInterface_thenFunctionAsArgumentsOrReturnValue() {
        // liuyang 20220505 add
        HigherOrderFunctionClass higherOrderFunctionClass = new HigherOrderFunctionClass();
        IFactory<User> factory = higherOrderFunctionClass.createFactory(
                () -> User.builder().id(100l).name("imooc").build(),
                (user) -> {
                    log.debug("用户信息:{}", user);
                    user.setMobile("13012345678");
                }
        );
        User user = factory.create();
        assertEquals("imooc", user.getName());
        assertEquals(100l, user.getId());
        assertEquals("13012345678", user.getMobile());
    }

    // Java中，把有且仅有一个未实现的非静态方法的接口叫做“函数式接口”。（即：Java可以用一个函数来实现这个接口。）
    interface IFactory<T> {
        T create();// 有且仅有一个，未实现的，非静态方法。
    }

    interface IProducer<T> {
        T produce();// 有且仅有一个，未实现的，非静态方法。
    }

    interface IConfigurator<T> {
        void configure(T t);// 有且仅有一个，未实现的，非静态方法。
    }

    class TestProducer implements IProducer {

        @Override
        public Object produce() {
            return null;
        }
    }

    static class HigherOrderFunctionClass {
        public <T> IFactory<T> createFactory(IProducer<T> producer, IConfigurator<T> configurator) {
            return () -> {
                T instance = producer.produce();
                configurator.configure(instance);
                return instance;
            };
        }
    }

    // liuyang 202205051902 add
    // 1-3 12:45左右
    @Test
    void testJDK8BuiltIn() {
        // 1. Function
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/package-summary.html
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/Function.html
        // 表示一个函数(方法)，它接收一个单一的参数并返回一个单一的值。
        Function<Long, Long> some = (value) -> value + 3;
        Long resultLambda = some.apply((long) 8);
        // 演示函数组合（顺序）
        Function<Integer, Integer> squareOp = (value) -> value * value;
        Function<Integer, Integer> doubleOp = (value) -> 2 * value;
        Function<Integer, Integer> doubleThenSquare = doubleOp.andThen(squareOp);
        Function<Integer, Integer> doubleThenSquare2 = squareOp.compose(doubleOp);
        log.info("顺序组合 result = {}", doubleThenSquare.apply(4));
        log.info("顺序组合 result = {}", doubleThenSquare2.apply(4));

        // 2. Predicate
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/Predicate.html
        // 表示一个简单的函数，它只去一个值作为参数，并返回真或假。
        Predicate<Object> predicate = (value) -> value != null;
        // 演示函数组合（逻辑）
        Predicate<String> startWithA =  (text) -> text.startsWith("A");
        Predicate<String> endWithX = (text) -> text.endsWith("X");
        Predicate<String> combinationFuction = startWithA.and(endWithX);// 复合函数？ 应该叫函数的组合吧。
        log.info("逻辑组合 result = {}", combinationFuction.test("AX"));
        log.info("逻辑组合 result = {}", combinationFuction.test("AqqqqX"));
        log.info("逻辑组合 result = {}", combinationFuction.test("hello, world"));

        // 3. UnaryOperator
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/UnaryOperator.html
        // 代表一个操作，它接收一个参数，并返回一个向同类型的参数。
        UnaryOperator<User> userUnaryOperator = (user) -> {
            user.setName("New Name;");
            return user;
        };

        // 4. BinaryOperator
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/BinaryOperator.html
        // 代表接收两个参数并返回一个值的操作。
        BinaryOperator<Long> binaryOperator = (a, b) -> a + b;

        // 5. Supplier
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/Supplier.html
        // 代表一个函数，它提供了某种类型的值。
        Supplier<Integer> supplier = () -> (int) (Math.random() * 1000D);

        // 6. Consumer
        // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/function/Consumer.html
        // 代表一个接收一个参数而不返回任何值得函数。
        Consumer<Integer> consumer = (value) -> log.debug("{}", value);
    }
}
