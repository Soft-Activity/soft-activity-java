package homework.soft.activity.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-21 15:57
 */
@Data
public class ActivityRecentMonthStatVO {
    @Schema(description = "日期")
    private LocalDate  date;
    @Schema(description = "活动数量")
    private Integer activityCount;
    @Schema(description = "参与人数")
    private Integer totalParticipants;
}
