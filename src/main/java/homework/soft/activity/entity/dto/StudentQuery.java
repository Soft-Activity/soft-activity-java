package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生(Student)查询参数
 *
 * @author jscomet
 * @since 2024-11-24 15:00:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQuery extends Student {
}
