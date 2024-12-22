package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.ActivityLocation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 活动地点表(ActivityLocation)查询参数
 *
 * @author jscomet
 * @since 2024-12-21 23:23:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityLocationQuery extends ActivityLocation {
}
