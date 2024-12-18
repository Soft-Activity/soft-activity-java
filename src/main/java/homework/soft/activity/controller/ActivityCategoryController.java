package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.ActivityCategoryQuery;
import homework.soft.activity.entity.dto.ActivityCategoryStatQuery;
import homework.soft.activity.entity.po.ActivityCategory;
import homework.soft.activity.entity.vo.ActivityCategoryStatVO;
import homework.soft.activity.entity.vo.ActivityCategoryVO;
import homework.soft.activity.service.ActivityCategoryService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动分类表(ActivityCategory)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 14:59:12
 */
@Tag(name = "ActivityCategory", description = "活动分类表")
@RestController
@RequestMapping("/activityCategory")
public class ActivityCategoryController {
    @Resource
    private ActivityCategoryService activityCategoryService;

    @Operation(summary = "获取指定活动分类表信息")
    @GetMapping("/info/{id}")
    public CommonResult<ActivityCategoryVO> getActivityCategory(@PathVariable Integer id) {
        ActivityCategoryVO vo = activityCategoryService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取活动分类表列表")
    @GetMapping("/list")
    public CommonResult<ListResult<ActivityCategoryVO>> getActivityCategorys(@RequestParam(defaultValue = "1") Integer current,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             ActivityCategoryQuery param) {
        List<ActivityCategoryVO> list = activityCategoryService.queryAll(current, pageSize, param);
        int total = activityCategoryService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加活动分类表")
    @PostMapping("/add")
    public CommonResult<Boolean> addActivityCategory(@RequestBody ActivityCategory param) {
        if (param.getCategoryId() == null) {
            int maxId = activityCategoryService.getMaxActivityCategoryId();
            param.setCategoryId(maxId + 1);
        }
        return activityCategoryService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定活动分类表信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateActivityCategory(@PathVariable Integer id,
                                                        @RequestBody ActivityCategory param) {
        param.setCategoryId(id);
        return activityCategoryService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定活动分类表")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteActivityCategory(@PathVariable Integer id) {
        return activityCategoryService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取活动分类统计列表")
    @GetMapping("/statistics")
    public CommonResult<List<ActivityCategoryStatVO>> getActivityCategoryStatistics(ActivityCategoryStatQuery param) {
        List<ActivityCategoryStatVO> list = activityCategoryService.queryStatistics(param);
        return CommonResult.success(list);
    }
}
