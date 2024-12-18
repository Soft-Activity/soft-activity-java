package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * (UserRole)实体类
 *
 * @author jscomet
 * @since 2024-11-24 09:26:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = -30696759645873659L;
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @TableField(value = "user_id")
    private String userId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @TableField(value = "role_id")
    private Integer roleId;

}
