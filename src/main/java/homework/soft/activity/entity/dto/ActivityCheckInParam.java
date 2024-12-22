package homework.soft.activity.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 报名打卡参数
 *
 * @author 30597
 * @since 2024-12-22 1:19
 */
@Data
public class ActivityCheckInParam {
    /**
     * 活动id
     */
    @Schema(description = "活动id")
    @NotNull
    private Integer activityId;

    /**
     * 打卡经度
     */
    @Schema(description = "打卡经度")
    @NotNull
    private Double gcj02Longitude;
    /**
     * 打卡纬度
     */
    @Schema(description = "打卡纬度")
    @NotNull
    private Double gcj02Latitude;

}
