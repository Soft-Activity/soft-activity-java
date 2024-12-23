package homework.soft.activity.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-23 17:15
 */
@Data
public class RegistrationExportVO {

    @Schema(description = "学号")
    @Excel(name = "学号",width = 20)
    private String schoolId;
    @Schema(description = "用户名")
    @Excel(name = "用户名",width = 20)
    private String userName;
    @Schema(description = "学院名称")
    @Excel(name = "学院名称",width = 35)
    private String collegeName;
    @Schema(description = "打卡状态")
    @Excel(name = "打卡状态",width = 20, replace = {"已打卡_true", "未打卡_false"})
    public Boolean isCheckIn;
    @Schema(description = "报名时间")
    @Excel(name = "报名时间" ,width = 35, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static RegistrationExportVO of(RegistrationVO registrationVO) {
        RegistrationExportVO exportVO = new RegistrationExportVO();
        exportVO.setSchoolId(registrationVO.getSchoolId());
        exportVO.setUserName(registrationVO.getUserName());
        exportVO.setCollegeName(registrationVO.getCollegeName());
        exportVO.setIsCheckIn(registrationVO.getIsCheckIn());
        exportVO.setCreateTime(registrationVO.getCreateTime());
        return exportVO;
    }


}
