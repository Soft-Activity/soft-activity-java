package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.ActivityLocationQuery;
import homework.soft.activity.entity.po.ActivityLocation;
import homework.soft.activity.entity.vo.ActivityLocationVO;
import homework.soft.activity.service.ActivityLocationService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动地点表(ActivityLocation)表控制层
 *
 * @author jscomet
 * @since 2024-12-21 23:23:34
 */
@Tag(name = "ActivityLocation", description = "活动地点表")
@RestController
@RequestMapping("/activityLocation")
public class ActivityLocationController {
    @Resource
    private ActivityLocationService activityLocationService;

    @Operation(summary = "获取指定活动地点表信息")
    @GetMapping("/info/{id}")
    public CommonResult<ActivityLocationVO> getActivityLocation(@PathVariable Integer id) {
        ActivityLocationVO vo = activityLocationService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取活动地点表列表")
    @GetMapping("/list")
    public CommonResult<ListResult<ActivityLocationVO>> getActivityLocations(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            ActivityLocationQuery param) {
        List<ActivityLocationVO> list = activityLocationService.queryAll(current, pageSize, param);
        int total = activityLocationService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加活动地点表")
    @PostMapping("/add")
    public CommonResult<Boolean> addActivityLocation(@RequestBody ActivityLocation param) {
        return activityLocationService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定活动地点表信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateActivityLocation(@PathVariable Integer id,
            @RequestBody ActivityLocation param) {
            param.setLocationId(id);
        return activityLocationService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定活动地点表")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteActivityLocation(@PathVariable Integer id) {
        return activityLocationService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
