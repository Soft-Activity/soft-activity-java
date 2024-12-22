package homework.soft.activity.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;

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

    @Getter
    public enum Status {
        /**
         * 报名状态 0 已报名 1 已取消
         */
        REGISTERED(0),
        CANCELED(1);

        private final int value;

        Status(int value) {
            this.value = value;
        }

    }

    /**
     * 报名id
     */
    @Schema(description = "报名id")
    @TableId(value = "registration_id", type = IdType.AUTO)
    private Integer registrationId;


    /**
     * 学生用户id
     */
    @Schema(description = "学生用户id")
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    @TableField(value = "activity_id")
    private Integer activityId;

    /**
     * 报名状态 0 已报名 1 已取消
     */
    @Schema(description = "报名状态 0 已报名 1 已取消")
    @TableField(value = "status")
    private Integer status;

    /**
     * 报名时间
     */
    @Schema(description = "报名时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 是否已打卡 0未打卡 1已打卡
     */
    @Schema(description = "是否已打卡 0未打卡 1已打卡")
    @TableField(value = "is_check_in")
    private Boolean isCheckIn;

    /**
     * 打卡时间
     */
    @Schema(description = "打卡时间")
    @TableField(value = "check_in_time")
    private LocalDateTime checkInTime;

    /**
     * 打卡纬度
     */
    @Schema(description = "打卡纬度")
    @TableField(value = "check_in_lat")
    private Double checkInLat;

    /**
     * 打卡经度
     */
    @Schema(description = "打卡经度")
    @TableField(value = "check_in_lng")
    private Double checkInLng;


}
