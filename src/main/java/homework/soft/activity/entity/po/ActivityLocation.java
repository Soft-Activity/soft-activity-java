package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动地点表(ActivityLocation)实体类
 *
 * @author jscomet
 * @since 2024-12-21 23:23:34
 */
@Data
public class ActivityLocation implements Serializable {
    @Serial
    private static final long serialVersionUID = -30779644376167693L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")    
    @TableId(value = "location_id",type = IdType.AUTO)
    private Integer locationId;


    /**
     * 地点名称
     */
    @Schema(description = "地点名称")    
    @TableField(value = "name")
    private String name;

    /**
     * 纬度
     */
    @Schema(description = "纬度")    
    @TableField(value = "lat")
    private Double lat;

    /**
     * 经度
     */
    @Schema(description = "经度")    
    @TableField(value = "lng")
    private Double lng;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")    
    @TableField(value = "update_time")
    private LocalDateTime updateTime;


}
