package homework.soft.activity.entity.vo;

import homework.soft.activity.entity.po.ActivityCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 活动分类表(ActivityCategory)视图模型
 *
 * @author jscomet
 * @since 2024-11-24 14:59:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityCategoryVO extends ActivityCategory {
    private Integer activityCount;
}
