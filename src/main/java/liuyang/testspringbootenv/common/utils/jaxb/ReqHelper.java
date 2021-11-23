package liuyang.testspringbootenv.common.utils.jaxb;

import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SOAP服务处理Request对象时使用的辅助类
 * 注：ReqHelp和RespHelper作为特定使用场景下其他工具类的包装类。
 * 编写目的：为了调用时好记。
 *
 * @author liuyang(wx)
 * @since 2021/7/8
 */
@Slf4j
public class ReqHelper {

    /**
     * 将java.util.Date类型对象转换为*Request对象中所需的XMLGregorianCalendar对象
     * @param date 数据对象中的java.util.Date字段
     * @return *Request对象中使用的XMLGregorianCalendar对象。
     *          如果转换异常则返回null。
     */
    public static XMLGregorianCalendar parseDate(Date date) {
        if (null == date) {
            return null;
        }
        return XMLDateUtil.toXMLFormat(date);
    }

    /**
     * 将XMLGregorianCalendar类型对象转换为java.util.Date类型兑现
     * @param xgc *Request对象中使用的XMLGregorianCalendar对象。
     * @return java.util.Date类型对象
     */
    public static Date parseDate(XMLGregorianCalendar xgc) {
        if (null == xgc) {
            return null;
        }
        return XMLDateUtil.toDate(xgc);
    }

    /**
     * 将日期时间形式的字符串转换为*Request对象中所需的XMLGregorianCalendar对象
     * @param datetimeStr 格式为“yyyy-MM-dd HH:mm:ss”的字符串
     * @return  *Request对象中使用的XMLGregorianCalendar对象。
     *          如果转换异常则返回null。
     */
    public static XMLGregorianCalendar parseDatetimeStr(String datetimeStr) {
        if (null == datetimeStr) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return XMLDateUtil.toXMLFormat(sdf.parse(datetimeStr));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将日期形式的字符串转换为*Request对象中所需的XMLGregorianCalendar对象
     * @param dateStr 格式为“yyyy-MM-dd”的字符串
     * @return *Request对象中使用的XMLGregorianCalendar对象。
     *          如果转换异常则返回null。
     */
    public static XMLGregorianCalendar parseDateStr(String dateStr) {
        if (null == dateStr) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return XMLDateUtil.toXMLFormat(sdf.parse(dateStr));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
