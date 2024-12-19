package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.dto.StudentQuery;
import homework.soft.activity.entity.vo.StudentVO;
import org.apache.ibatis.annotations.Select;

/**
 * 学生(Student)表数据库访问层
 *
 * @author jscomet
 * @since 2024-11-24 15:00:02
 */
@Mapper
public interface StudentDao extends BaseMapper<Student> {
    /**
     * 通过ID查询单条数据
     *
     * @param studentId 主键
     * @return 实例对象
     */
    StudentVO queryById(String studentId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<StudentVO> queryAll(StudentQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(StudentQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<Student> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<Student> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<Student> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<Student> entities);
    /**
     * 获取学生学院列表
     * @return 对象列表
     */
    @Select("select college from student group by college")
    List<String> getCollegeList();
    /**
     * 获取学生班级列表
     * @param param 学院
     * @return 对象列表
     */
    List<String> getClassList(StudentQuery param);
}




