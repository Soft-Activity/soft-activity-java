package homework.soft.activity.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;

/**
 * 活动分类表(ActivityCategory)实体类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:12
 */
@Data
public class ActivityCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = -83645859466389637L;
    /**
     * 分类id
     */
    @Schema(description = "分类id")    
    @TableId(value = "category_id",type = IdType.AUTO)
    private Integer categoryId;


    /**
     * 分类名称
     */
    @Schema(description = "分类名称")    
    @TableField(value = "name")
    private String name;


}
