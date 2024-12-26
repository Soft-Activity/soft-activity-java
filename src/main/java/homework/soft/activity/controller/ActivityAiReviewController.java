package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.ActivityAiReviewCreateParam;
import homework.soft.activity.entity.dto.ActivityAiReviewQuery;
import homework.soft.activity.entity.po.ActivityAiReview;
import homework.soft.activity.entity.vo.ActivityAiReviewVO;
import homework.soft.activity.service.ActivityAiReviewService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (ActivityAiReview)表控制层
 *
 * @author makejava
 * @since 2024-12-01 22:25:01
 */
@Tag(name = "ActivityAiReview", description = "ai分析")
@RestController
@RequestMapping("/activityAiReview")
public class ActivityAiReviewController {
    @Resource
    private ActivityAiReviewService activityAiReviewService;

    @Operation(summary = "获取指定信息")
    @GetMapping("/info/{id}")
    public CommonResult<ActivityAiReviewVO> getActivityAiReview(@PathVariable Integer id) {
        ActivityAiReviewVO vo = activityAiReviewService.queryByIdCache(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.success(null);
    }

    @Operation(summary = "获取列表")
    @GetMapping("/list")
    public CommonResult<ListResult<ActivityAiReviewVO>> getActivityAiReviews(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            ActivityAiReviewQuery param) {
        List<ActivityAiReviewVO> list = activityAiReviewService.queryAll(current, pageSize, param);
        int total = activityAiReviewService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加")
    @PostMapping("/add")
    public CommonResult<Boolean> addActivityAiReview(@RequestBody ActivityAiReviewCreateParam param) {
        return activityAiReviewService.insert(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateActivityAiReview(@PathVariable Integer id,
            @RequestBody ActivityAiReviewCreateParam param) {
        return activityAiReviewService.update(id, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteActivityAiReview(@PathVariable Integer id) {
        return activityAiReviewService.deleteById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
