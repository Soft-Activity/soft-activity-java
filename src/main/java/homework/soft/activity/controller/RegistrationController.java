package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.RegistrationVO;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报名表(Registration)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
@Tag(name = "Registration", description = "报名表")
@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Resource
    private RegistrationService registrationService;

    @Operation(summary = "获取指定报名表信息")
    @GetMapping("/info/{id}")
    public CommonResult<RegistrationVO> getRegistration(@PathVariable Integer id) {
        RegistrationVO vo = registrationService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取报名表列表")
    @GetMapping("/list")
    public CommonResult<ListResult<RegistrationVO>> getRegistrations(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            RegistrationQuery param) {
        List<RegistrationVO> list = registrationService.queryAll(current, pageSize, param);
        int total = registrationService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加报名表")
    @PostMapping("/add")
    public CommonResult<Boolean> addRegistration(@RequestBody Registration param) {
        if (param.getRegistrationId() == null) {
            int maxId = registrationService.getMaxRegistrationId();
            param.setRegistrationId(maxId + 1);
        }
        return registrationService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定报名表信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateRegistration(@PathVariable Integer id,
            @RequestBody Registration param) {
            param.setRegistrationId(id);
        return registrationService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定报名表")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteRegistration(@PathVariable Integer id) {
        return registrationService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
