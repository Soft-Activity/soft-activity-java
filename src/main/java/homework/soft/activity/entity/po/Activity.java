package homework.soft.activity.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Activity)实体类
 *
 * @author jscomet
 * @since 2024-11-24 14:58:50
 */
@Data
public class Activity implements Serializable {
    @Serial
    private static final long serialVersionUID = 984277655182483547L;
    /**
     * 活动id
     */
    @Schema(description = "活动id")    
    @TableId(value = "activity_id",type = IdType.AUTO)
    private Integer activityId;


    /**
     * 活动名称
     */
    @Schema(description = "活动名称")    
    @TableField(value = "name")
    private String name;

    /**
     * 组织者学号id
     */
    @Schema(description = "组织者学号id")
    @TableField(value = "organizer_id")
    private String organizerId;

    /**
     * 分类id
     */
    @Schema(description = "分类id")    
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 活动地点
     */
    @Schema(description = "活动地点")    
    @TableField(value = "location")
    private String location;

    /**
     * 描述
     */
    @Schema(description = "描述")    
    @TableField(value = "description")
    private String description;

    /**
     * 是否取消 0未开始 1进行中 2 已结束 3 已取消
     */
    @Schema(description = "是否取消 0未开始 1进行中 2 已结束 3 已取消")
    @TableField(value = "status")
    private Integer status;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")    
    @TableField(value = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")    
    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 最大容量
     */
    @Schema(description = "最大容量")    
    @TableField(value = "max_capacity")
    private Integer maxCapacity;

    /**
     * 当前容量
     */
    @Schema(description = "当前容量")    
    @TableField(value = "capacity")
    private Integer capacity;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 是否需要打卡 0否 1是
     */
    @Schema(description = "是否需要打卡 0否 1是")
    @TableField(value = "is_check_in")
    private Boolean isCheckIn;

    /**
     * 打卡地点ID
     */
    @Schema(description = "打卡地点ID")
    @TableField(value = "check_in_location_id")
    private Integer checkInLocationId;

    /**
     * 打卡范围(单位:米)
     */
    @Schema(description = "打卡范围(单位:米)")
    @TableField(value = "check_in_radius")
    private Integer checkInRadius;

    /**
     * 打卡开始时间
     */
    @Schema(description = "打卡开始时间")
    @TableField(value = "check_in_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkInStartTime;

    /**
     * 打卡结束时间
     */
    @Schema(description = "打卡结束时间")
    @TableField(value = "check_in_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkInEndTime;


}
