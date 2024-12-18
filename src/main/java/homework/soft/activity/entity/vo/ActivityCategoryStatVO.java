package homework.soft.activity.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-18 15:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityCategoryStatVO extends ActivityCategoryVO {
    /**
     * 总活动数
     */
    @Schema(description = "总活动数")
    private Integer totalActivities;

    /**
     * 未开始活动数
     */
    @Schema(description = "未开始活动数")
    private Integer notStarted;

    /**
     * 进行中活动数
     */
    @Schema(description = "进行中活动数")
    private Integer ongoing;

    /**
     * 已结束活动数
     */
    @Schema(description = "已结束活动数")
    private Integer ended;

    /**
     * 已取消活动数
     */
    @Schema(description = "已取消活动数")
    private Integer cancelled;

    /**
     * 总参与人数
     */
    @Schema(description = "总参与人数")
    private Integer totalParticipants;

    /**
     * 总容量
     */
    @Schema(description = "总容量")
    private Integer totalCapacity;

}
