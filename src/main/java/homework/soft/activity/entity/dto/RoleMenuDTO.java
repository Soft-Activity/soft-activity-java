package homework.soft.activity.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-20 17:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuDTO {
    private Integer roleId;
    private List<Long> menuIds;
}