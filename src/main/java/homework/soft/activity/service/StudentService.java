package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.dto.ImportTotalResult;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.dto.StudentQuery;
import homework.soft.activity.entity.vo.StudentVO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 学生(Student)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 15:00:02
 */
public interface StudentService extends IService<Student> {
    /**
     * 通过ID查询单条数据
     *
     * @param studentId 主键
     * @return 实例对象
     */
    StudentVO queryById(String studentId);

    /**
     * 查询多条数据
     *
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
     * @return 对象列表
     */
    List<StudentVO> queryAll(int current, int pageSize, StudentQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(StudentQuery param);

    /**
     * 获取学生学院列表
     *
     * @return 对象列表
     */
    List<String> getCollegeList();

    /**
     * 获取学生班级列表
     *
     * @param param 参数
     * @return 对象列表
     */
    List<String> getClassList(StudentQuery param);

    /**
     * 批量导入学生信息
     *
     * @param list 学生信息列表
     * @return 导入结果
     */
    ImportTotalResult batchImport(List<Student> list);

    /**
     * 添加学生信息
     *
     * @param student 学生信息
     * @return 是否成功
     */
    boolean addStudent(Student student);
}

