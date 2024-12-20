package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.RoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色-菜单关联表(RoleMenu)查询参数
 *
 * @author jscomet
 * @since 2024-12-20 15:56:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleMenuQuery extends RoleMenu {
}
