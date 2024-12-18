package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.StudentDao;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.service.StudentService;
import homework.soft.activity.entity.dto.StudentQuery;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.vo.StudentVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

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

    @Override
    public StudentVO queryById(String studentId) {
        return studentDao.queryById(studentId);
    }

    @Override
    public List<StudentVO> queryAll(int current, int pageSize, StudentQuery param) {
        PageHelper.startPage(current, pageSize);
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
    public List<String> getClassList(String college) {
        return studentDao.getClassList(college);
    }

}

