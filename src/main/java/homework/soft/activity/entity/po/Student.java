package homework.soft.activity.entity.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 学生(Student)实体类
 *
 * @author jscomet
 * @since 2024-11-24 15:00:02
 */
@Data
@EqualsAndHashCode(exclude = {"isVerified"})
public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = -76004887998213437L;
    /**
     * 学生学号
     */
    @Schema(description = "学生学号")
    @TableId(value = "student_id", type = IdType.INPUT)
//    @JsonProperty("student_id")
    @Excel(name = "学号", width = 15)
    private String studentId;


    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @TableField(value = "name")
    @Excel(name = "姓名", width = 15)

    private String name;

    /**
     * 学院
     */
    @Schema(description = "学院")
    @TableField(value = "college")
    @Excel(name = "学院", width = 15)
    private String college;

    /**
     * 班级
     */
    @Schema(description = "班级")
    @TableField(value = "classes")
    @Excel(name = "班级", width = 15)
    private String classes;

    /**
     * 年级
     */
    @Schema(description = "年级")
    @TableField(value = "grade")
    @Excel(name = "年级", width = 15)
    private Integer grade;

    /**
     * 类型:本科生/研究生/博士生
     */
    @Schema(description = "类型:本科生/研究生/博士生")
    @TableField(value = "type")
    @Excel(name = "类型", width = 15, addressList = true, replace = {"本科生_本科生", "研究生_研究生", "博士生_博士生"})
    private String type;

    /**
     * 性别 男/女
     */
    @Schema(description = "性别 男/女")
    @TableField(value = "gender")
    @Excel(name = "性别", width = 15, replace = {"男_男", "女_女"}, addressList = true)
    private String gender;

    /**
     * 是否已认证0为认证，1已认证
     */
    @Schema(description = "是否已认证0为认证，1已认证")
    @TableField(value = "is_verified")
    @Excel(name = "是否已认证", width = 15, replace = {"是_true", "否_false", "_null"})
    private Boolean isVerified;


}
