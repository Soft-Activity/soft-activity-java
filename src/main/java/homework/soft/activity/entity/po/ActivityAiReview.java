package homework.soft.activity.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(value = "activity_id")
    private Integer activityId;

    @Schema(description = "ai分析")
    @TableField(value = "ai_analysis")
    private String aiAnalysis;

/**
     * rank >= 4的人数
     */
     @Schema(description = "rank > 3的人数")
     @TableField(value = "good_num")
    private Integer goodNum;

/**
     * rank = 3
     */
     @Schema(description = "rank = 3")
     @TableField(value = "medium_num")
    private Integer mediumNum;

/**
     * rank < 3 的人数
     */
     @Schema(description = "rank < 3 的人数")
     @TableField(value = "poor_num")
    private Integer poorNum;

/**
     * 平均分
     */
     @Schema(description = "平均分")
     @TableField(value = "average_score")
    private double averageScore;



}

