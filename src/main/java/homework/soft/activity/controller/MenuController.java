package homework.soft.activity.controller;

import homework.soft.activity.annotation.PermissionAuthorize;
import homework.soft.activity.entity.dto.MenuQuery;
import homework.soft.activity.entity.dto.RoleMenuDTO;
import homework.soft.activity.entity.po.Menu;
import homework.soft.activity.entity.vo.MenuVO;
import homework.soft.activity.entity.vo.RouteVO;
import homework.soft.activity.service.MenuService;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单表(Menu)表控制层
 *
 * @author jscomet
 * @since 2024-12-20 15:55:42
 */
@Tag(name = "Menu", description = "菜单表")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;


    @Operation(summary = "获取我的路由信息")
    @GetMapping("/routes-self")
    @PermissionAuthorize
    public CommonResult<RouteVO> getMyRoutes() {
        String userId = AuthUtils.getCurrentUserId();
        return CommonResult.success(menuService.getUserRoutes(userId));
    }

    @GetMapping("/list")
    @PermissionAuthorize
    @Operation(summary = "获取菜单表列表")
    public CommonResult<List<MenuVO>> getMenuList(MenuQuery query) {
        return CommonResult.success(menuService.getMenuList(query));
    }

    @GetMapping("/role/{roleId}")
    @Schema(description = "获取角色菜单")
    public CommonResult<List<MenuVO>> getRoleMenus(@PathVariable Integer roleId) {
        return CommonResult.success(menuService.getRoleMenus(roleId));
    }

    @PostMapping("/assign")
    @Schema(description = "分配菜单")
    public CommonResult<Boolean> assignMenu(@RequestBody RoleMenuDTO roleMenuDTO) {
        return CommonResult.success(menuService.assignMenu(roleMenuDTO));
    }

    @PostMapping("/add")
    @Schema(description = "新增菜单")
    public CommonResult<Boolean> addMenu(@RequestBody Menu menu) {
        return CommonResult.success(menuService.save(menu));
    }
    @PostMapping("/update")
    @Schema(description = "更新菜单")
    public CommonResult<Boolean> updateMenu(@RequestBody Menu menu) {
        AssertUtils.notNull(menu.getMenuId(), HttpStatus.BAD_REQUEST, "菜单ID不能为空");
        return CommonResult.success(menuService.updateById(menu));
    }
}
