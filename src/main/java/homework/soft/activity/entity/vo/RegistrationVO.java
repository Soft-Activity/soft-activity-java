package homework.soft.activity.entity.vo;

import homework.soft.activity.entity.po.Registration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报名表(Registration)视图模型
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegistrationVO extends Registration {
    @Schema(description = "用户名")
    private String userName;
    @Schema(description = "学院名称")
    private String collegeName;
    @Schema(description = "学号")
    private String schoolId;
}
