package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.RoleDao;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.service.RoleService;
import homework.soft.activity.entity.dto.RoleQuery;
import homework.soft.activity.entity.vo.RoleVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 角色(Role)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 13:14:07
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public RoleVO queryById(Integer roleId) {
        return roleDao.queryById(roleId);
    }

    @Override
    public List<RoleVO> queryAll(int current, int pageSize, RoleQuery param) {
        PageHelper.startPage(current, pageSize);
        return roleDao.queryAll(param);
    }

    @Override
    public int count(RoleQuery param) {
        return roleDao.count(param);
    }

    @Override
    public List<Role> queryByUserId(String userId) {
        return roleDao.queryByUserId(userId);
    }


}

