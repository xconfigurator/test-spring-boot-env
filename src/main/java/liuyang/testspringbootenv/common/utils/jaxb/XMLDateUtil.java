package liuyang.testspringbootenv.common.utils.jaxb;

import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 提供JAXB对象（存放在dto包中）使用的XMLGregorianCalendar对象和java.util.Date互转方法
 * @author liuyang(wx)
 * @since 2021/6/15
 */
@Slf4j
public class XMLDateUtil {

    /**
     * 使用场景：调用soap服务前，填写*Request对象的日期字段时使用
     * @param date 待转换java.util.Date类型对象
     * @return JAXB对象中使用的XMLGregorianCalendar对象, 若转换发生异常则返回null
     */
    public static XMLGregorianCalendar toXMLFormat(Date date) {
        if (null == date) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            log.error(e.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 适用场景：接收从soap服务返回的*Response对象相关字段时使用
     * @param xgc 待转换类型（一般从JAXB对象的相关字段获取该类型数据）
     * @return java.util.Date
     */
    public static Date toDate(XMLGregorianCalendar xgc) {
        if (null == xgc) {
            return null;
        }
        return xgc.toGregorianCalendar().getTime();
    }
}
