package homework.soft.activity.controller;

import homework.soft.activity.annotation.PermissionAuthorize;
import homework.soft.activity.entity.dto.ActivityCheckInParam;
import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.RegistrationExportVO;
import homework.soft.activity.entity.vo.RegistrationVO;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.service.impl.UserServiceImpl;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.ExcelUtils;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private UserServiceImpl userService;
    @Resource
    private ActivityService activityService;

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

    @Operation(summary = "报名活动")
    @PostMapping("/register/{activityId}")
    @PermissionAuthorize
    public CommonResult<Boolean> registerActivity(@PathVariable Integer activityId) {
        AssertUtils.isTrue(AuthUtils.isAuthenticated(), HttpStatus.UNAUTHORIZED, "请先登录");
        String userId = AuthUtils.getCurrentUserId();
        return registrationService.registerActivity(userId, activityId) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "打卡活动")
    @PostMapping("/check-in")
    @PermissionAuthorize
    public CommonResult<Boolean> checkInActivity(@RequestBody @Validated ActivityCheckInParam param) {
        AssertUtils.isTrue(AuthUtils.isAuthenticated(), HttpStatus.UNAUTHORIZED, "请先登录");
        String userId = AuthUtils.getCurrentUserId();
        return registrationService.checkInActivity(userId, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "是否已打卡")
    @PostMapping("/check-in/{activityId}")
    @PermissionAuthorize
    public CommonResult<Boolean> isCheckin(@PathVariable Integer activityId) {
        String userId = AuthUtils.getCurrentUserId();
        return CommonResult.success(registrationService.isCheckIn(userId, activityId));
    }

    @Operation(summary = "获取我是否报名过活动")
    @GetMapping("/is-apply/{activityId}")
    public CommonResult<Boolean> isRegister(@PathVariable Integer activityId) {
        String userId = AuthUtils.getCurrentUserId();
        return CommonResult.success(registrationService.isRegister(userId, activityId));
    }

    @Operation(summary = "取消报名")
    @DeleteMapping("/cancel/{activityId}")
    public CommonResult<Boolean> cancelRegister(@PathVariable Integer activityId) {
        String userId = AuthUtils.getCurrentUserId();
        return registrationService.cancelRegister(userId, activityId) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定报名表")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteRegistration(@PathVariable Integer id) {
        return registrationService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "导出活动报名人数列表")
    @GetMapping("/export/{activityId}")
    public void exportRegistrations(@PathVariable Integer activityId, HttpServletResponse response) throws IOException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Activity activity = activityService.queryById(activityId);
        AssertUtils.notNull(activity, HttpStatus.NOT_FOUND, "活动不存在");

        RegistrationQuery query = new RegistrationQuery();
        query.setActivityId(activityId);
        query.setStatus(Registration.Status.REGISTERED.getValue());
        List<RegistrationExportVO> registrations = registrationService.queryAll(-1, -1, query)
                .stream().map(RegistrationExportVO::of)
                .collect(Collectors.toCollection(ArrayList::new));
        ExcelUtils.downloadExcel(response, RegistrationExportVO.class, registrations, activity.getName() + "报名人数列表");
    }
}
