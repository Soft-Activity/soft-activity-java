package homework.soft.activity.entity.vo;

import lombok.*;

import java.util.List;

/**
 * 菜单表(Menu)视图模型
 *
 * @author jscomet
 * @since 2024-12-20 15:55:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {
    private Long menuId;
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private String redirect;
    private MetaVO meta;
    private Integer sortOrder;
    private Integer status;
    private List<MenuVO> children;
}
