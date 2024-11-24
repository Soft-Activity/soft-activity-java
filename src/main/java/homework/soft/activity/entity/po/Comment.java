package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Comment)实体类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:31
 */
@Data
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 413353620904842772L;
    /**
     * 评论id
     */
    @Schema(description = "评论id")    
    @TableId(value = "comment_id")
    private Integer commentId;


    /**
     * 报名id
     */
    @Schema(description = "报名id")    
    @TableField(value = "activity_id")
    private Integer activityId;

    /**
     * 学生号
     */
    @Schema(description = "学生号")    
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 评分0-5
     */
    @Schema(description = "评分0-5")    
    @TableField(value = "rating")
    private Integer rating;

    /**
     * 内容
     */
    @Schema(description = "内容")    
    @TableField(value = "content")
    private String content;

    /**
     * 评论时间
     */
    @Schema(description = "评论时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
