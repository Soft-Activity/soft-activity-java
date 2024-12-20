package homework.soft.activity.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-20 16:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaVO {
    private String title;
    private String icon;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Boolean noCache;
    private Boolean breadcrumb;
    private String activeMenu;
    private Boolean noTagsView;
    private Boolean canTo;
    private List<String> permission; // 存储角色的 ename
}