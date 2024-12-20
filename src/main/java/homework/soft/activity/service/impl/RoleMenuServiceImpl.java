package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.RoleMenuDao;
import homework.soft.activity.entity.po.RoleMenu;
import homework.soft.activity.service.RoleMenuService;
import homework.soft.activity.entity.dto.RoleMenuQuery;
import homework.soft.activity.entity.vo.RoleMenuVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 角色-菜单关联表(RoleMenu)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-20 15:56:09
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {
    @Resource
    private RoleMenuDao roleMenuDao;

    @Override
    public RoleMenuVO queryById(Long id) {
        return roleMenuDao.queryById(id);
    }

    @Override
    public List<RoleMenuVO> queryAll(int current, int pageSize, RoleMenuQuery param) {
        PageHelper.startPage(current, pageSize);
        return roleMenuDao.queryAll(param);
    }

    @Override
    public int count(RoleMenuQuery param) {
        return roleMenuDao.count(param);
    }

    @Override
    public boolean deleteByRoleId(Integer roleId) {
        return this.lambdaUpdate().eq(RoleMenu::getRoleId, roleId).remove();
    }

    @Override
    public List<RoleMenu> selectByRoleId(Integer roleId) {
        return this.lambdaQuery().eq(RoleMenu::getRoleId, roleId).list();
    }
}

