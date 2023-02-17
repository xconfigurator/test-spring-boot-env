package liuyang.testspringbootenv.modules.utils.easyexcel20220611.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author liuyang(wx)
 * @since 2022/6/11
 */
@Data
public class AIOpsAlarmDictionary {
    // 告警标题
    @ExcelProperty("告警标题")
    private String alarmTitle;

    // 告警标题翻译
    @ExcelProperty("告警标题翻译")
    private String alarmTitleTranslation;

    // 显示标题
    @ExcelProperty("显示标题")
    private String displayTitle;

    // 告警类型
    @ExcelProperty("告警类型")
    private String alarmType;

    // 重定义告警类型
    @ExcelProperty("重定义告警类型")
    private String alarmTypeRedefine;

    // 告警编码
    @ExcelProperty("告警编码")
    private String alarmCode;

    // 告警等级
    @ExcelProperty("告警等级")
    private String alarmLevel;

    // 重定义告警等级
    @ExcelProperty("重定义告警等级")
    private String alarmLevelRedefine;

    // 告警设备状态
    @ExcelProperty("告警设备状态")
    private String equipState;

    // 所属线路
    @ExcelProperty("所属线路")
    private String line;

    // 所属系统
    @ExcelProperty("所属系统")
    private String system;

    // 设备型号
    @ExcelProperty("设备型号")
    private String equipModel;

    // 启用状态
    @ExcelProperty("启用状态")
    private String upOrDown;

    // 版本信息
    @ExcelProperty("版本信息")
    private String version;

    // 告警文本
    @ExcelProperty("告警文本")
    private String alarmInfo;

    // 告警文本翻译
    @ExcelProperty("告警文本翻译")
    private String alarmInfoTranslation;

    // 告警原因
    @ExcelProperty("告警原因")
    private String alarmReason;

    // 告警处理建议
    @ExcelProperty("告警处理建议")
    private String recommendation;// advice or recommandation
}
