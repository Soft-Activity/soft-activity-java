package homework.soft.activity.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户(User)实体类
 *
 * @author jscomet
 * @since 2024-11-24 11:31:18
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -62836068019278205L;
    /**
     * 用户id
     */
    @Schema(description = "学号/学工号")    
    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;


    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @TableField(value = "name")
    private String name;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @TableField(value = "password")
    @JsonIgnore
    private String password;

    /**
     * 微信openId
     */
    @Schema(description = "微信openId")
    @TableField(value = "open_id")
    @JsonIgnore
    private String openId;

    /**
     * 学号
     */
    @Schema(description = "学号")
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 学院/部门
     */
    @Schema(description = "学院/部门")    
    @TableField(value = "college")
    private String college;

    /**
     * 图片路径
     */
    @Schema(description = "图片路径")    
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 性别
     */
    @Schema(description = "性别")    
    @TableField(value = "gender")
    private String gender;


}
