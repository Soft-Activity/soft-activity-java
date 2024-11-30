package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.RoleQuery;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.vo.RoleVO;
import homework.soft.activity.service.RoleService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色(Role)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 13:14:04
 */
@Tag(name = "Role", description = "角色")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Operation(summary = "获取指定角色信息")
    @GetMapping("/info/{id}")
    public CommonResult<RoleVO> getRole(@PathVariable Integer id) {
        RoleVO vo = roleService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public CommonResult<ListResult<RoleVO>> getRoles(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            RoleQuery param) {
        List<RoleVO> list = roleService.queryAll(current, pageSize, param);
        int total = roleService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加角色")
    @PostMapping("/add")
    public CommonResult<Boolean> addRole(@RequestBody Role param) {
        if (param.getRoleId() == null) {
            int maxId = roleService.getMaxRoleId();
            param.setRoleId(maxId + 1);
        }
        return roleService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定角色信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateRole(@PathVariable Integer id,
            @RequestBody Role param) {
            param.setRoleId(id);
        return roleService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定角色")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteRole(@PathVariable Integer id) {
        return roleService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
