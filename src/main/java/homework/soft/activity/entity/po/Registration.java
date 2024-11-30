package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报名表(Registration)实体类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
@Data
public class Registration implements Serializable {
    @Serial
    private static final long serialVersionUID = -61770044101547791L;
    /**
     * 报名id
     */
    @Schema(description = "报名id")    
    @TableId(value = "registration_id")
    private Integer registrationId;


    /**
     * 学生学号
     */
    @Schema(description = "学生学号")    
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 活动id
     */
    @Schema(description = "活动id")    
    @TableField(value = "activity_id")
    private Integer activityId;

    /**
     * 报名状态 0 未开始、1 进行中、2 已结束、3 已取消
     */
    @Schema(description = "报名状态 0 未开始、1 进行中、2 已结束、3 已取消")
    @TableField(value = "status")
    private Integer status;

    /**
     * 报名时间
     */
    @Schema(description = "报名时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
