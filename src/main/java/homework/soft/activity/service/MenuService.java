package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.dto.RoleMenuDTO;
import homework.soft.activity.entity.po.Menu;
import homework.soft.activity.entity.dto.MenuQuery;
import homework.soft.activity.entity.vo.MenuVO;
import homework.soft.activity.entity.vo.RouteVO;

import java.util.List;

/**
 * 菜单表(Menu)表服务接口
 *
 * @author jscomet
 * @since 2024-12-20 15:55:42
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过用户id获取路由
     * @param userId 用户id
     * @return 路由信息
     */
    RouteVO getUserRoutes(String userId);

    List<MenuVO> getMenuList(MenuQuery query);

    List<MenuVO> getRoleMenus(Integer roleId);

    boolean assignMenu(RoleMenuDTO roleMenuDTO);
}

