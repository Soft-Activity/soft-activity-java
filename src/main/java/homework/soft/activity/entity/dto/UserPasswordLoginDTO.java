package homework.soft.activity.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户账号密码登录
 *
 * @author jscomet
 * @since 2024-11-24 11:31:21
 */
@Data
public class UserPasswordLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1218323211L;
    @Schema(description = "学号")
    @NotBlank
    private String studentId;
    @Schema(description = "密码")
    @NotBlank
    private String password;
}
