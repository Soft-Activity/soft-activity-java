package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.RoleMenu;
import homework.soft.activity.entity.dto.RoleMenuQuery;
import homework.soft.activity.entity.vo.RoleMenuVO;

import java.util.List;

/**
 * 角色-菜单关联表(RoleMenu)表服务接口
 *
 * @author jscomet
 * @since 2024-12-20 15:56:09
 */
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleMenuVO queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<RoleMenuVO> queryAll(int current, int pageSize, RoleMenuQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(RoleMenuQuery param);

    /**
     * 根据角色id删除角色菜单关联
     *
     * @param roleId 角色id
     * @return 是否成功
     */
    boolean deleteByRoleId(Integer roleId);
    /**
     * 根据角色id查询角色菜单关联
     *
     * @param roleId 角色id
     * @return 角色菜单关联
     */
    List<RoleMenu> selectByRoleId(Integer roleId);
}

