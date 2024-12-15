package homework.soft.activity.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;

/**
 * 角色(Role)实体类
 *
 * @author jscomet
 * @since 2024-11-24 13:14:05
 */
@Data
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 281059597746332475L;
    /**
     * 用户id
     */
    @Schema(description = "用户id")    
    @TableId(value = "role_id",type = IdType.AUTO)
    private Integer roleId;


    /**
     * 角色中文名称
     */
    @Schema(description = "角色中文名称")    
    @TableField(value = "cname")
    private String cname;

    /**
     * 角色英文名称
     */
    @Schema(description = "角色英文名称")    
    @TableField(value = "ename")
    private String ename;


}
