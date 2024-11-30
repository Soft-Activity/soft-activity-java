package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.CommentQuery;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.vo.CommentVO;
import homework.soft.activity.service.CommentService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Comment)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 14:59:31
 */
@Tag(name = "Comment", description = "")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @Operation(summary = "获取指定信息")
    @GetMapping("/info/{id}")
    public CommonResult<CommentVO> getComment(@PathVariable Integer id) {
        CommentVO vo = commentService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取列表")
    @GetMapping("/list")
    public CommonResult<ListResult<CommentVO>> getComments(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            CommentQuery param) {
        List<CommentVO> list = commentService.queryAll(current, pageSize, param);
        int total = commentService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加")
    @PostMapping("/add")
    public CommonResult<Boolean> addComment(@RequestBody Comment param) {
        if (param.getCommentId() == null) {
            int maxId = commentService.getMaxCommentId();
            param.setCommentId(maxId + 1);
        }
        return commentService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateComment(@PathVariable Integer id,
            @RequestBody Comment param) {
            param.setCommentId(id);
        return commentService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteComment(@PathVariable Integer id) {
        return commentService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
