package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.UserDao;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.po.User;
import homework.soft.activity.service.RoleService;
import homework.soft.activity.service.UserService;
import homework.soft.activity.entity.dto.UserQuery;
import homework.soft.activity.entity.vo.UserVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * 用户(User)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 11:31:20
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleService roleService;

    @Override
    public UserVO queryById(String userId) {
        UserVO user = userDao.queryById(userId);
        this.fillVO(user);
        return userDao.queryById(userId);
    }

    private void fillVO(UserVO userVO) {
        String userId = userVO.getUserId();
        if (userId != null) {
            List<Role> roles = roleService.queryByUserId(userId);
            userVO.setRoles(roles);
        }
    }

    @Override
    public List<UserVO> queryAll(int current, int pageSize, UserQuery param) {
        PageHelper.startPage(current, pageSize);
        List<UserVO> list = userDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    @Override
    public int count(UserQuery param) {
        return userDao.count(param);
    }
}

