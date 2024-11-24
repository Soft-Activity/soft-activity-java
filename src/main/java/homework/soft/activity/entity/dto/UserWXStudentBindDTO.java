package homework.soft.activity.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信学生信息登录凭证
 *
 * @author jscomet
 * @since 2024-11-24 11:31:21
 */
@Data
public class UserWXStudentBindDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2378139113211L;
    @Schema(description = "微信登录凭证")
    @NotBlank
    private String code;

    /**
     * 学生学号
     */
    @Schema(description = "学生学号")
    @NotBlank
    private String studentId;


    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotBlank
    private String name;

    /**
     * 学院
     */
    @Schema(description = "学院")
    @NotBlank
    private String college;

    /**
     * 班级
     */
    @Schema(description = "班级")
    @NotBlank
    private String classes;

    /**
     * 年级
     */
    @Schema(description = "年级")
    @NotNull
    private Integer grade;

    /**
     * 类型:本科生/研究生/博士生
     */
    @Schema(description = "类型:本科生/研究生/博士生")
    @NotBlank
    private String type;

    /**
     * 性别 男/女
     */
    @Schema(description = "性别 男/女")
    @NotBlank
    private String gender;

    /**
     * 是否已认证0为认证，1已认证
     */
    @Schema(description = "是否已认证0为认证，1已认证")
    @NotNull
    private Boolean isVerified;
}
