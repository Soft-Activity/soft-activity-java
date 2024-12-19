package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.dao.StudentDao;
import homework.soft.activity.entity.dto.ImportRowResult;
import homework.soft.activity.entity.dto.ImportTotalResult;
import homework.soft.activity.entity.dto.UserCreateParm;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.po.User;
import homework.soft.activity.service.StudentService;
import homework.soft.activity.entity.dto.StudentQuery;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.vo.StudentVO;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生(Student)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 15:00:02
 */
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
    @Resource
    private StudentDao studentDao;
    @Resource
    private UserService userService;

    @Override
    public StudentVO queryById(String studentId) {
        return studentDao.queryById(studentId);
    }

    @Override
    public List<StudentVO> queryAll(int current, int pageSize, StudentQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        return studentDao.queryAll(param);
    }

    @Override
    public int count(StudentQuery param) {
        return studentDao.count(param);
    }

    @Override
    public List<String> getCollegeList() {
        return studentDao.getCollegeList();
    }

    @Override
    public List<String> getClassList(StudentQuery param) {
        return studentDao.getClassList(param);
    }

    @Override
    public ImportTotalResult batchImport(List<Student> list) {
        ImportTotalResult result = new ImportTotalResult();
        result.setFailed(new ArrayList<>());
        result.setSuccess(new ArrayList<>());
        for (Student student : list) {
            if (student == null) {
                continue;
            }
            try {
                //1.校验学号
                String studentId = student.getStudentId();
                if (!checkStudentId(studentId)) {
                    result.getFailed().add(ImportRowResult.builder()
                            .success(false)
                            .message("学号格式错误: " + studentId).build());
                    continue;
                }
                //2. 校验学号是否存在
                boolean isExist = this.lambdaQuery().eq(Student::getStudentId, studentId).count() > 0;
                if (isExist) {
                    result.getFailed().add(ImportRowResult.builder()
                            .success(false)
                            .message("学号已存在: " + studentId).build());
                    continue;
                }
                //3. 保存
                boolean success = super.save(student);
                if (!success) {
                    result.getFailed().add(ImportRowResult.builder()
                            .success(false)
                            .message("保存失败: " + studentId).build());
                    continue;
                }

                //4. 导入成功
                result.getSuccess().add(ImportRowResult.builder()
                        .success(true)
                        .message("导入成功: " + studentId).build());
            } catch (Exception e) {
                result.getFailed().add(ImportRowResult.builder()
                        .success(false)
                        .message("导入失败: " + student.getStudentId() + ", 异常：" + e.getMessage()).build());
                continue;
            }
        }
        return result;
    }

    @Override
    @Transactional
    public boolean addStudent(Student student) {
        AssertUtils.notNull(student, HttpStatus.BAD_REQUEST, "学生信息不能为空");
        AssertUtils.notBlank(student.getStudentId(), HttpStatus.BAD_REQUEST, "学号不能为空");

        boolean isExit = this.lambdaQuery().eq(Student::getStudentId, student.getStudentId()).exists();
        AssertUtils.isTrue(!isExit, HttpStatus.BAD_REQUEST, "学号重复");

        return super.save(student);
    }

    private boolean checkStudentId(String studentId) {
        if (StringUtils.isBlank(studentId)) {
            return false;
        }
        //至少要10位以上数字
        return !studentId.matches("202\\d{8}");
    }

}

