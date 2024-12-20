package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色-菜单关联表(RoleMenu)实体类
 *
 * @author jscomet
 * @since 2024-12-20 15:56:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色-菜单关联表")
public class RoleMenu implements Serializable {
    @Serial
    private static final long serialVersionUID = 673083010341218651L;
    /**
     * ID
     */
    @Schema(description = "ID")    
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /**
     * 角色ID
     */
    @Schema(description = "角色ID")    
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")    
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
