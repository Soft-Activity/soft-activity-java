package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.UserDao;
import homework.soft.activity.entity.po.Account;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.po.User;
import homework.soft.activity.entity.vo.UserAuthVO;
import homework.soft.activity.service.AccountService;
import homework.soft.activity.service.RoleService;
import homework.soft.activity.service.StudentService;
import homework.soft.activity.service.UserService;
import homework.soft.activity.entity.dto.UserQuery;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.JwtUtils;
import homework.soft.activity.util.WeChatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Resource
    private AccountService accountService;
    @Resource
    private StudentService studentService;

    @Override
    public UserVO queryById(String userId) {
        UserVO user = userDao.queryById(userId);
        this.fillVO(user);
        return user;
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

    private boolean verifyPassword(String userId, String password) {
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Account::getUserId, userId)
                .eq(Account::getPassword, password);
        return accountService.count(queryWrapper) > 0;
    }

    private boolean verifyStudent(String userId, Student param) {
        Student student = studentService.queryById(userId);
        if (student == null) {
            return false;
        }
        student.setIsVerified(null);
        param.setIsVerified(null);
        //比较两者信息是否相同
        return student.equals(param);
    }

    private String getOpenIdByCode(String code) {
        //1.获取用户openId
        WeChatUtils.AppletSession appletSession = WeChatUtils.getAppletSessionByCode(code);
        return Optional.ofNullable(appletSession)
                .map(WeChatUtils.AppletSession::getOpenid)
                .orElse(null);
    }


    @Override
    public UserAuthVO loginByPassword(String userId, String password) {
        // 1.查询账号信息
        AssertUtils.isTrue(verifyPassword(userId, password), HttpStatus.NOT_FOUND, "查无账号信息");
        //2.获得token
        String token = JwtUtils.createJWTByUserId(userId);
        return new UserAuthVO(token);
    }

    @Override
    public UserAuthVO loginByWX(String code) {

        String openId = getOpenIdByCode(code);
        AssertUtils.isTrue(StringUtils.isNotBlank(openId), HttpStatus.INTERNAL_SERVER_ERROR, "微信登录失败");
        //2. 查询用户
        Account account = accountService.lambdaQuery().eq(Account::getOpenId, openId).one();
        AssertUtils.notNull(account, HttpStatus.FORBIDDEN, "查无账号信息");

        //2.获得token
        String token = JwtUtils.createJWTByUserId(account.getUserId());
        return new UserAuthVO(token);
    }

    @Override
    @Transactional
    public UserAuthVO bindWxByPassword(String code, String userId, String password) {
        // 1.校验账号信息
        AssertUtils.isTrue(verifyPassword(userId, password), HttpStatus.NOT_FOUND, "校验失败");

        //2. 查询用户
        Account account = accountService.lambdaQuery().eq(Account::getUserId, userId).one();
        AssertUtils.notNull(account, HttpStatus.NOT_FOUND, "查无账号信息");

        //3.校验是否已绑定微信
        AssertUtils.notNull(account.getOpenId(), HttpStatus.BAD_REQUEST, "用户已绑定微信账号");

        //4.获取用户openId
        String openId = getOpenIdByCode(code);
        AssertUtils.isTrue(StringUtils.isNotBlank(openId), HttpStatus.INTERNAL_SERVER_ERROR, "微信登录失败");
        //5.更新账号信息
        LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Account::getOpenId, openId).eq(Account::getUserId, userId);
        AssertUtils.isTrue(accountService.update(updateWrapper), HttpStatus.INTERNAL_SERVER_ERROR, "绑定失败");
        //6.返回信息
        String token = JwtUtils.createJWTByUserId(account.getUserId());

        return new UserAuthVO(token);
    }

    @Override
    @Transactional
    public UserAuthVO bindWxByStudentInfo(String code, Student student) {

        String userId = student.getStudentId();
        // 1.校验账号信息
        AssertUtils.isTrue(verifyStudent(userId, student), HttpStatus.NOT_FOUND, "校验失败");

        //2. 查询用户
        Account account = accountService.lambdaQuery().eq(Account::getUserId, userId).one();
        AssertUtils.notNull(account, HttpStatus.NOT_FOUND, "查无账号信息");

        //3.校验是否已绑定微信
        AssertUtils.notNull(account.getOpenId(), HttpStatus.BAD_REQUEST, "用户已绑定微信账号");

        //4.获取用户openId
        String openId = getOpenIdByCode(code);
        AssertUtils.isTrue(StringUtils.isNotBlank(openId), HttpStatus.INTERNAL_SERVER_ERROR, "微信登录失败");
        //5.更新账号信息
        LambdaUpdateWrapper<Account> updateAccount = new LambdaUpdateWrapper<>();
        updateAccount.set(Account::getOpenId, openId).eq(Account::getUserId, userId);
        AssertUtils.isTrue(accountService.update(updateAccount), HttpStatus.INTERNAL_SERVER_ERROR, "绑定失败");
        //6.更新认证状态
        LambdaUpdateWrapper<Student> updateStudent = new LambdaUpdateWrapper<>();
        updateStudent.set(Student::getIsVerified,true).eq(Student::getStudentId, userId);
        AssertUtils.isTrue(studentService.update(updateStudent),HttpStatus.INTERNAL_SERVER_ERROR,"绑定失败");

        //7.返回信息
        String token = JwtUtils.createJWTByUserId(account.getUserId());

        return new UserAuthVO(token);
    }


}

