package homework.soft.activity.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-18 15:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityCategoryStatQuery extends ActivityCategoryQuery {

    /**
     * 活动状态
     */
    @Schema(description = "活动状态")
    private Integer activityStatus;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndTime;

}
