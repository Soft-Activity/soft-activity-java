package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.StudentQuery;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.vo.StudentVO;
import homework.soft.activity.service.StudentService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生(Student)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 15:00:02
 */
@Tag(name = "Student", description = "学生")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Operation(summary = "获取指定学生信息")
    @GetMapping("/info/{id}")
    public CommonResult<StudentVO> getStudent(@PathVariable String id) {
        StudentVO vo = studentService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取学生列表")
    @GetMapping("/list")
    public CommonResult<ListResult<StudentVO>> getStudents(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            StudentQuery param) {
        List<StudentVO> list = studentService.queryAll(current, pageSize, param);
        int total = studentService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加学生")
    @PostMapping("/add")
    public CommonResult<Boolean> addStudent(@RequestBody Student param) {
        if (checkStudentId(param.getStudentId())) {
            return CommonResult.error(HttpStatus.BAD_REQUEST, "学号格式错误");
        }
        return studentService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定学生信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateStudent(@PathVariable String id,
            @RequestBody Student param) {
            param.setStudentId(id);
        return studentService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定学生")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteStudent(@PathVariable String id) {
        if (checkStudentId(id)) {
            return CommonResult.error(HttpStatus.BAD_REQUEST, "学号格式错误");
        }
        return studentService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "获取学生学院列表")
    @GetMapping("/college-list")
    public CommonResult<List<String>> getCollegeList() {
        return CommonResult.success(studentService.getCollegeList());
    }
    @Operation(summary = "获取学生班级列表")
    @GetMapping("/class-list/{college}")
    public CommonResult<List<String>> getClassList(@PathVariable @NotBlank String college) {
        return CommonResult.success(studentService.getClassList(college));
    }

//    抽象学号校验
    private boolean checkStudentId(String studentId) {
        String regex = "202\\d{8}";
        return studentId.matches(regex);
    }
}
