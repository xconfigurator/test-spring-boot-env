/**
 * 【注】
 * 本包只存放JSON相关的内容。
 * 目前（20220607）最新的序列化套件在test-client-httpclient4/modules/apache/httpclient4/utils/fastjson下的HttpClientUtil和JsonUtil。
 *      HttpClientUtil会直接调用JsonUtil来进行序列化，同时通过JsonUtil来对使用的基础GSON序列化工具进行特性配置。
 *
 *
 * 【结论（关于日期）】
 * Date和LocalDateTime的格式要实现说清楚！！！！！
 *
 * 【Fastjson】
 *  java.util.Date
 *      默认
 *      "testDate":1649829073276
 *  java.time.LocalDateTime
 *      默认
 *      "testJSR310Date":"2022-04-13T13:51:13.283686300"
 *
 * 【Jackson】
 *  java.util.Date
 *      "testDate":1649828951087
 *  java.time.LocalDateTime
 *      默认不支持，需定制
 *      定制om.registerModule(new JavaTimeModule());后格式：
 *      "testJSR310Date":[2022,4,13,13,49,11,94400100]
 *
 * 【Gson】
 *  java.util.Date
 *      默认不支持，需定制
 *  java.time.LocalDateTime
 *      默认不支持，需定制
 *
 * @author liuyang(wx)
 * @since 2022/4/13
 */
package liuyang.testspringbootenv.modules.json;