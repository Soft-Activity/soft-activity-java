package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户(User)查询参数
 *
 * @author jscomet
 * @since 2024-11-24 11:31:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends User {

    @Schema(description = "角色id")
    private Integer roleId;
}
