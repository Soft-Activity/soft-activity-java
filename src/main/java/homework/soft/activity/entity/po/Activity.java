package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "activity_id")
    private Integer activityId;


    /**
     * 活动名称
     */
    @Schema(description = "活动名称")    
    @TableField(value = "name")
    private String name;

    /**
     * 组织者学号
     */
    @Schema(description = "组织者学号")    
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
     * 是否取消 0未取消，1取消
     */
    @Schema(description = "是否取消 0未取消，1取消")    
    @TableField(value = "status")
    private Integer status;

    /**
     * 活动开始时间
     */
    @Schema(description = "活动开始时间")    
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")    
    @TableField(value = "end_time")
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
    private LocalDateTime createTime;


}
