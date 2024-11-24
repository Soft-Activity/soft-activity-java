package homework.soft.activity.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信老师登录认证参数
 *
 * @author jscomet
 * @since 2024-11-24 11:31:21
 */
@Data
public class UserWXPasswordBindDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1278193711L;
    @Schema(description = "微信登录凭证")
    @NotBlank
    private String code;

    @Schema(description = "学号")
    @NotBlank
    private String userId;
    @Schema(description = "密码")
    @NotBlank
    private String password;

}
