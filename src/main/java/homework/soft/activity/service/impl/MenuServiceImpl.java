package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homework.soft.activity.dao.MenuDao;
import homework.soft.activity.entity.dto.MenuQuery;
import homework.soft.activity.entity.dto.RoleMenuDTO;
import homework.soft.activity.entity.po.Menu;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.po.RoleMenu;
import homework.soft.activity.entity.vo.MetaVO;
import homework.soft.activity.entity.vo.RouteVO;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.service.MenuService;
import homework.soft.activity.entity.vo.MenuVO;
import homework.soft.activity.service.RoleMenuService;
import homework.soft.activity.service.RoleService;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AssertUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单表(Menu)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-20 15:55:42
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
    @Resource
    private MenuDao menuDao;
    @Resource
    private UserService useService;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private RoleService roleService;

    @Override
    public RouteVO getUserRoutes(String userId) {
        //判断用户是否存在
        UserVO user = useService.queryById(userId);
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "用户不存在");

        // 1. 获取用户角色
        List<Role> roles = user.getRoles();
        List<String> permissions = roles.stream()
                .map(Role::getEname)
                .collect(Collectors.toList());

        // 2. 获取角色对应的菜单
        List<Menu> menus = menuDao.selectMenusByRoleIds(
                roles.stream()
                        .map(Role::getRoleId)
                        .collect(Collectors.toList())
        );

        // 3. 构建树形菜单
        List<MenuVO> routes = this.buildTree(menus, permissions);

        // 4. 返回路由信息
        return RouteVO.builder()
                .routes(routes)
                .permissions(permissions)
                .build();
    }

    public MenuVO toVO(Menu menu, List<String> permissions) {
        if (menu == null) {
            return null;
        }

        return MenuVO.builder()
                .menuId(menu.getMenuId())
                .parentId(menu.getParentId())
                .name(menu.getName())
                .path(menu.getPath())
                .component(menu.getComponent())
                .redirect(menu.getRedirect())
                .meta(buildMeta(menu, permissions))
                .status(menu.getStatus())
                .sortOrder(menu.getSortOrder())
                .build();
    }

    private MetaVO buildMeta(Menu menu, List<String> permissions) {
        return MetaVO.builder()
                .title(menu.getTitle())
                .icon(menu.getIcon())
                .hidden(menu.getHidden())
                .alwaysShow(menu.getAlwaysShow())
                .noCache(menu.getKeepAlive())
                .breadcrumb(menu.getBreadcrumb())
                .activeMenu(menu.getActiveMenu())
                .noTagsView(menu.getNoTagsView())
                .canTo(menu.getCanTo())
                .permission(permissions)
                .build();
    }

    // 构建树形结构
    public List<MenuVO> buildTree(List<Menu> menus, List<String> permissions) {
        List<MenuVO> tree = new ArrayList<>();
        Map<Long, MenuVO> menuMap = new HashMap<>();

        // 转换所有菜单
        for (Menu menu : menus) {
            MenuVO vo = toVO(menu, permissions);
            menuMap.put(menu.getMenuId(), vo);
        }

        // 构建树形结构
        for (Menu menu : menus) {
            MenuVO current = menuMap.get(menu.getMenuId());
            if (menu.getParentId() == 0) {
                tree.add(current);
            } else {
                MenuVO parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(current);
                }
            }
        }

        return tree;
    }

    // 构建树形结构
    public List<MenuVO> buildTree(List<Menu> menus, Map<Long, List<String>> permissions) {
        List<MenuVO> tree = new ArrayList<>();
        Map<Long, MenuVO> menuMap = new HashMap<>();

        // 转换所有菜单
        for (Menu menu : menus) {
            MenuVO vo = toVO(menu, permissions.get(menu.getMenuId()));
            menuMap.put(menu.getMenuId(), vo);
        }

        // 构建树形结构
        for (Menu menu : menus) {
            MenuVO current = menuMap.get(menu.getMenuId());
            if (menu.getParentId() == 0) {
                tree.add(current);
            } else {
                MenuVO parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(current);
                }
            }
        }

        return tree;
    }

    @Override
    public List<MenuVO> getMenuList(MenuQuery query) {
        // 获取所有菜单
        List<Menu> menus = menuDao.queryAll(query);

        // 获取所有角色菜单的目录权限
        List<RoleMenu> roleMenus = roleMenuService.list();
        // 获取所有角色信息
        Map<Integer, String> rolePermissions = roleService.list().stream()
                .collect(Collectors.toMap(Role::getRoleId, Role::getEname));
        // 获取所有菜单的权限
        Map<Long, List<String>> menuPermissions = roleMenus.stream()
                .collect(Collectors.groupingBy(
                        RoleMenu::getMenuId,
                        Collectors.mapping(roleMenu -> rolePermissions.get(roleMenu.getRoleId()), Collectors.toList())
                ));
        return this.buildTree(menus, menuPermissions);
    }

    @Override
    public List<MenuVO> getRoleMenus(Integer roleId) {
        // 获取角色的菜单
        List<Menu> menus = menuDao.selectMenusByRoleIds(List.of(roleId));

        Role role = roleService.getById(roleId);
        return this.buildTree(menus, List.of(role.getEname()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean assignMenu(RoleMenuDTO roleMenuDTO) {
        Integer roleId = roleMenuDTO.getRoleId();

        // 1. 删除原有的角色-菜单关联
        roleMenuService.deleteByRoleId(roleId);

        // 2. 保存新的角色-菜单关联
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : roleMenuDTO.getMenuIds()) {
            roleMenus.add(RoleMenu.builder()
                    .roleId(roleId)
                    .menuId(menuId)
                    .build());
        }

        if (!roleMenus.isEmpty()) {
            roleMenuService.saveBatch(roleMenus);
        }

        return true;
    }
}

