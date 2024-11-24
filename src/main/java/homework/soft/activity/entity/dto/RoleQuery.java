package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色(Role)查询参数
 *
 * @author jscomet
 * @since 2024-11-24 13:14:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQuery extends Role {
}
