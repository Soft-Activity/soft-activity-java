package homework.soft.activity.entity.vo;

import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户(User)视图模型
 *
 * @author jscomet
 * @since 2024-11-24 11:31:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {
    @Schema(description = "角色列表")
    private List<Role> roles;
    @Schema(description = "是否绑定微信")
    private Boolean bindWX;
    @Schema(description = "是否设置密码")
    private Boolean setPassword;
}
