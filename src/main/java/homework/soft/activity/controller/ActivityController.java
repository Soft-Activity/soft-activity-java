package homework.soft.activity.controller;

import homework.soft.activity.annotation.PermissionAuthorize;
import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.entity.dto.ActivityQuery;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.vo.ActivityVO;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Activity)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 14:58:50
 */
@Tag(name = "Activity", description = "活动模块")
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @Operation(summary = "获取指定信息")
    @GetMapping("/info/{id}")
    public CommonResult<ActivityVO> getActivity(@PathVariable Integer id) {
        ActivityVO vo = activityService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取列表")
    @GetMapping("/list")
    public CommonResult<ListResult<ActivityVO>> getActivitys(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            ActivityQuery param) {
        List<ActivityVO> list = activityService.queryAll(current, pageSize, param);
        int total = activityService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加")
    @PostMapping("/add")
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<Boolean> addActivity(@RequestBody Activity param) {
        if (param.getActivityId() == null) {
            int maxId = activityService.getMaxActivityId();
            param.setActivityId(maxId + 1);
        }
        return activityService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateActivity(@PathVariable Integer id,
            @RequestBody Activity param) {
            param.setActivityId(id);
        return activityService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteActivity(@PathVariable Integer id) {
        return activityService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
