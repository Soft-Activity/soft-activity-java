package homework.soft.activity.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户重置密码DTO
 *
 * @author 30597
 * @since 2024-12-19 13:52
 */
@Data
public class UserResetPasswordDTO {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
