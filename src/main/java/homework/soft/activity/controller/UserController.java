package homework.soft.activity.controller;

import cn.hutool.core.bean.BeanUtil;
import homework.soft.activity.annotation.PermissionAuthorize;
import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.constant.enums.UploadModule;
import homework.soft.activity.entity.dto.*;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.po.User;
import homework.soft.activity.entity.vo.UserAuthVO;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.property.AppProperty;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.UploadUtils;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.afterturn.easypoi.util.PoiFunctionUtil.length;

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
    @Resource
    private AppProperty appProperty;

    @Operation(summary = "获取指定用户信息")
    @GetMapping("/info/{id}")
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<UserVO> getUser(@PathVariable String id) {
        try {
            UserVO vo = userService.queryById(id);
            return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return CommonResult.error(HttpStatus.BAD_REQUEST, "账号错误");
        }
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
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<Boolean> addUser(@RequestBody UserCreateParm param) {
        return userService.saveNewUserForAdmin(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "用户注册失败,请联系管理员");
    }

    @Operation(summary = "修改指定用户信息")
    @PutMapping("/update/{id}")
    @PermissionAuthorize
    public CommonResult<Boolean> updateUser(@PathVariable String id, @RequestBody UserCreateParm param) {
        // 只有超级管理员和用户本人可以修改用户信息
        AssertUtils.isTrue(AuthUtils.getUserDetails().getUserId().equals(id) || AuthUtils.hasAnyRole(RoleType.SUPER_ADMIN), HttpStatus.FORBIDDEN, "无权更新");

        // 超级管理员可以修改用户角色
        if (!AuthUtils.hasAnyRole(RoleType.SUPER_ADMIN)) {
            param.setRoleIds(null);
        }
        return userService.updateUser(id, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "用户信息更新失败,请联系管理员");
    }

    @Operation(summary = "删除指定用户")
    @DeleteMapping("/delete/{id}")
    @PermissionAuthorize(RoleType.SUPER_ADMIN)
    public CommonResult<Boolean> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "微信解绑")
    @PostMapping("/unbind-wx/{userId}")
    @PermissionAuthorize({RoleType.TEACHER, RoleType.TEACHER})
    public CommonResult<Boolean> unbindWXByUserId(@PathVariable String userId) {
        return userService.unbindWX(userId) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "解绑失败");
    }

    @Operation(summary = "重新设置学生密码")
    @PostMapping("/reset-password")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> resetPassword(@RequestBody UserResetPasswordDTO param) {
        return userService.resetPassword(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "重置密码失败");
    }

    //用户个人功能软件
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/current")
    public CommonResult<UserVO> getCurrentUser() {
        UserVO vo = AuthUtils.getUserDetails();
        return CommonResult.success(vo);
    }

    @Operation(summary = "用户上传头像")
    @PostMapping("/upload-avatar")
    public CommonResult<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = appProperty.getBack().getUrl() + "/view-avatar" + UploadUtils.upload(file, UploadModule.USER_AVATAR.toString());
        return CommonResult.success(filename);
    }

    @Operation(summary = "用户查看头像")
    @GetMapping("/view-avatar/{filename}")
    public ResponseEntity<org.springframework.core.io.Resource> viewAvatar(@PathVariable String filename) throws IOException {

        return UploadUtils.getResponseEntity(UploadModule.USER_AVATAR.toString(), filename);
    }



    @Operation(summary = "用户账号密码登录")
    @PostMapping("/login-by-password")
    public CommonResult<UserAuthVO> loginByPassword(@RequestBody @Validated UserPasswordLoginDTO param) {
        UserAuthVO auth = userService.loginByPassword(param.getStudentId(), param.getPassword());
        return CommonResult.success(auth);
    }

    @Operation(summary = "用户微信登录")
    @PostMapping("/login-by-wx")
    public CommonResult<UserAuthVO> loginByWX(@RequestBody @Validated UserWXLoginDTO param) {
        UserAuthVO auth = userService.loginByWX(param.getCode());
        return CommonResult.success(auth);
    }

    @Operation(summary = "微信用户通过账号密码绑定账号")
    @PostMapping("/bind-wx-by-password")
    public CommonResult<UserAuthVO> bindWxByPassword(@RequestBody @Validated UserWXPasswordBindDTO param) {
        UserAuthVO auth = userService.bindWxByPassword(param.getCode(), param.getStudentId(), param.getPassword());
        return CommonResult.success(auth);
    }

    @Operation(summary = "微信用户通过学生信息绑定账号")
    @PostMapping("/bind-wx-by-student-info")
    public CommonResult<UserAuthVO> bindWxByStudentInfo(@RequestBody @Validated UserWXStudentBindDTO param) {
        Student student = new Student();
        BeanUtil.copyProperties(param, student);
        UserAuthVO auth = userService.bindWxByStudentInfo(param.getCode(), student);
        return CommonResult.success(auth);
    }

    @Operation(summary = "微信自行解绑")
    @PostMapping("/unbind-wx-self")
    @PermissionAuthorize
    public CommonResult<Boolean> unbindMyWX() {
        String userId = AuthUtils.getUserDetails().getUserId();
        return userService.unbindWX(userId) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "解绑失败");
    }

    @Operation(summary = "用户修改密码")
    @PostMapping("/change-password")
    @PermissionAuthorize
    public CommonResult<Boolean> changePassword(@RequestBody @Validated UserChangePasswordDTO param) {
        String userId = AuthUtils.getUserDetails().getUserId();
        return userService.changePassword(userId, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "解绑失败");
    }
}
