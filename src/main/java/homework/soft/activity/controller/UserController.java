package homework.soft.activity.controller;

import homework.soft.activity.annotation.PermissionAuthorize;
import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.entity.dto.UserQuery;
import homework.soft.activity.entity.po.User;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户(User)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 11:31:17
 */
@Tag(name = "User", description = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Operation(summary = "获取指定用户信息")
    @GetMapping("/info/{id}")
    public CommonResult<UserVO> getUser(@PathVariable String id) {
        UserVO vo = userService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<ListResult<UserVO>> getUsers(@RequestParam(defaultValue = "1") Integer current,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     UserQuery param) {
        List<UserVO> list = userService.queryAll(current, pageSize, param);
        int total = userService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public CommonResult<Boolean> addUser(@RequestBody User param) {
        return userService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定用户信息")
    @PutMapping("/update/{id}")
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<Boolean> updateUser(@PathVariable String id, @RequestBody User param) {
        param.setUserId(id);
        return userService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定用户")
    @DeleteMapping("/delete/{id}")
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<Boolean> deleteUser(@PathVariable String id) {
        return userService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/current")
    public CommonResult<UserVO> getCurrentUser() {
        UserVO vo = AuthUtils.getUserDetails();
        return CommonResult.success(vo);
    }

}
