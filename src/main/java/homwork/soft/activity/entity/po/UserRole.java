package homwork.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;

/**
 * (UserRole)实体类
 *
 * @author jscomet
 * @since 2024-12-17 22:46:17
 */
@Data
public class UserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 676057775898921847L;
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")    
    private Integer roleId;



}
