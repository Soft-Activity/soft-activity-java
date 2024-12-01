package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * (ActivityAiReview)实体类
 *
 * @author makejava
 * @since 2024-12-01 22:25:02
 */
@Data
public class ActivityAiReview implements Serializable {
    @Serial
    private static final long serialVersionUID = -77227842357064911L;

    @Schema(description = "活动id")
    private Integer activityId;

    @Schema(description = "ai分析")
    private String aiAnalysis;

/**
     * rank >= 4的人数
     */
     @Schema(description = "rank > 3的人数")
    private Integer goodNum;

/**
     * rank = 3
     */
     @Schema(description = "rank = 3")
    private Integer mediumNum;

/**
     * rank < 3 的人数
     */
     @Schema(description = "rank < 3 的人数")
    private Integer poorNum;

/**
     * 平均分
     */
     @Schema(description = "平均分")
    private double averageScore;



}

