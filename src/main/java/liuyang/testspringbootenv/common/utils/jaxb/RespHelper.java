package liuyang.testspringbootenv.common.utils.jaxb;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * SOAP服务处理Reponse对象时使用的辅助类
 * 注：ReqHelp和RespHelper作为特定使用场景下其他工具类的包装类。
 * 编写目的：为了调用时好记。
 *
 * @author liuyang(wx)
 * @since 2021/7/8
 */
public class RespHelper {

    /**
     * @param date  java.util.Date
     * @return      SOAP服务*Response对象中使用的日期对象
     */
    public static XMLGregorianCalendar parseDate(Date date) {
        if (null == date) {
            return null;
        }
        return XMLDateUtil.toXMLFormat(date);
    }
}
