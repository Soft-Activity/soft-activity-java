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
 * @since 2024-12-20 16:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteVO {
    private List<MenuVO> routes;
    private List<String> permissions; // 用户所有的权限列表
}
