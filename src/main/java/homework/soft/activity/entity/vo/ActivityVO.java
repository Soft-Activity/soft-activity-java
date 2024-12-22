package homework.soft.activity.entity.vo;

import homework.soft.activity.entity.po.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * (Activity)视图模型
 *
 * @author jscomet
 * @since 2024-11-24 14:58:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityVO extends Activity {

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "组织者姓名")
    private String organizerName;

    @Schema(description = "平均评分")
    private Double avgRating;

    @Schema(description = "最近的评论")
    private List<CommentVO> recentComments;

    @Schema(description = "评论总数")
    private Integer commentCount;

    @Schema(description = "已打卡人数")
    private Integer checkInCount;

    @Schema(description = "打卡地点名称")
    private String checkInLocationName;

}
