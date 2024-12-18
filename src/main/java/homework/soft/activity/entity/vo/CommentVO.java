package homework.soft.activity.entity.vo;

import homework.soft.activity.entity.po.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Comment)视图模型
 *
 * @author jscomet
 * @since 2024-11-24 14:59:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends Comment {
    @Schema(description = "活动名称")
    private String activityName;

    @Schema(description = "评论者")
    private UserVO student;
}
