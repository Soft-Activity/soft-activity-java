package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.dao.UserDao;
import homework.soft.activity.entity.dto.UserChangePasswordDTO;
import homework.soft.activity.entity.dto.UserCreateParm;
import homework.soft.activity.entity.dto.UserResetPasswordDTO;
import homework.soft.activity.entity.po.*;
import homework.soft.activity.entity.vo.UserAuthVO;
import homework.soft.activity.exception.HttpErrorException;
import homework.soft.activity.service.*;
import homework.soft.activity.entity.dto.UserQuery;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.util.AssertUtils;
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
    private StudentService studentService;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public UserVO queryById(String userId) {
        UserVO user = userDao.queryById(userId);
        this.fillVO(user);
        return user;
    }

    private void fillVO(UserVO userVO) {
        if (userVO == null) {
            return;
        }
        String userId = userVO.getUserId();
        if (userId != null) {
            List<Role> roles = roleService.queryByUserId(userId);
            userVO.setRoles(roles);
        }
        userVO.setBindWX(StringUtils.isNotBlank(userVO.getOpenId()));
        userVO.setSetPassword(StringUtils.isNotBlank(userVO.getPassword()));
    }

    @Override
    public List<UserVO> queryAll(int current, int pageSize, UserQuery param) {
        if(current>=0 && pageSize>=0){
            PageHelper.startPage(current, pageSize);
        }
        List<UserVO> list = userDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    @Override
    public int count(UserQuery param) {
        return userDao.count(param);
    }

    private boolean verifyPassword(String studentId, String password) {
        User user = this.lambdaQuery().eq(User::getStudentId, studentId).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "查无账号信息");

        AssertUtils.isTrue(user.getPassword().equals(password), HttpStatus.BAD_REQUEST, "密码错误");
        return true;
    }

    private boolean verifyStudent(String studentId, Student param) {
        Student student = studentService.getById(studentId);
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
    public UserAuthVO loginByPassword(String studentId, String password) {
        // 1.查询账号信息
        AssertUtils.isTrue(verifyPassword(studentId, password), HttpStatus.NOT_FOUND, "校验失败");

        //2. 查询用户
        User user = this.lambdaQuery().eq(User::getStudentId, studentId).one();
        //2.获得token
        String token = JwtUtils.createJWTByUserId(user.getUserId());
        return new UserAuthVO(token);
    }

    @Override
    public UserAuthVO loginByWX(String code) {

        String openId = getOpenIdByCode(code);
        AssertUtils.isTrue(StringUtils.isNotBlank(openId), HttpStatus.INTERNAL_SERVER_ERROR, "微信登录失败");
        //2. 查询用户
        User user = this.lambdaQuery().eq(User::getOpenId, openId).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "查无账号信息");
        //3. 判断用户是否已认证
        AssertUtils.isTrue(StringUtils.isNotBlank(user.getStudentId()), HttpStatus.BAD_REQUEST, "用户未认证");
        //4.获得token
        String token = JwtUtils.createJWTByUserId(user.getUserId());
        return new UserAuthVO(token);
    }

    @Override
    @Transactional
    public UserAuthVO bindWxByPassword(String code, String studentId, String password) {
        // 1.校验账号信息
        AssertUtils.isTrue(verifyPassword(studentId, password), HttpStatus.NOT_FOUND, "校验失败");

        //2. 查询用户
        User user = this.lambdaQuery().eq(User::getStudentId, studentId).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "查无账号信息");

        //3.获取用户openId
        String openId = getOpenIdByCode(code);
        AssertUtils.isTrue(StringUtils.isNotBlank(openId), HttpStatus.INTERNAL_SERVER_ERROR, "微信登录失败");

        //4.校验是否已绑定微信
        AssertUtils.isTrue(StringUtils.isBlank(user.getOpenId()) || user.getOpenId().equals(openId),
                HttpStatus.BAD_REQUEST, "用户已绑定微信账号");

        //5.更新账号信息
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getOpenId, openId).eq(User::getUserId, user.getUserId());
        AssertUtils.isTrue(this.update(updateWrapper), HttpStatus.INTERNAL_SERVER_ERROR, "绑定失败");

        //7.更新认证状态(密码登录不需要校验是否成功)
        LambdaUpdateWrapper<Student> updateStudent = new LambdaUpdateWrapper<>();
        updateStudent.set(Student::getIsVerified, true).eq(Student::getStudentId, studentId);
        studentService.update(updateStudent);


        //8.返回信息
        String token = JwtUtils.createJWTByUserId(user.getUserId());

        return new UserAuthVO(token);
    }

    @Override
    @Transactional
    public UserAuthVO bindWxByStudentInfo(String code, Student student) {

        String studentId = student.getStudentId();
        // 1.校验账号信息
        AssertUtils.isTrue(verifyStudent(studentId, student), HttpStatus.BAD_REQUEST, "校验失败");

        //2. 查询用户
        User user = this.lambdaQuery().eq(User::getStudentId, studentId).one();

        if (user == null) {
            // 2.1. 根据学生信息创建用户
            user = new User();
            user.setStudentId(studentId);
            user.setName(student.getName());
            user.setCollege(student.getCollege());
            user.setGender(student.getGender());
            this.save(user);
        }
        //3.获取用户openId
        String openId = getOpenIdByCode(code);
        AssertUtils.isTrue(StringUtils.isNotBlank(openId), HttpStatus.INTERNAL_SERVER_ERROR, "微信登录失败");


        //4.校验是否已绑定微信,或者当前登录用户就是绑定的用户
        AssertUtils.isTrue(StringUtils.isBlank(user.getOpenId()) || user.getOpenId().equals(openId), HttpStatus.BAD_REQUEST, "用户已绑定微信账号");

        //5.更新账号信息
        LambdaUpdateWrapper<User> updateAccount = new LambdaUpdateWrapper<>();
        updateAccount.set(User::getOpenId, openId).eq(User::getUserId, user.getUserId());
        AssertUtils.isTrue(this.update(updateAccount), HttpStatus.INTERNAL_SERVER_ERROR, "绑定失败");
        //6.更新认证状态
        LambdaUpdateWrapper<Student> updateStudent = new LambdaUpdateWrapper<>();
        updateStudent.set(Student::getIsVerified, true).eq(Student::getStudentId, studentId);
        AssertUtils.isTrue(studentService.update(updateStudent), HttpStatus.INTERNAL_SERVER_ERROR, "绑定失败");

        //7. 添加用户学生角色
        UserRole userRole = new UserRole(user.getUserId(), RoleType.STUDENT.getRoleId());
        //如果没有就保存
        if (userRoleService.getOne(new QueryWrapper<>(userRole)) == null) {
            userRoleService.save(userRole);
        }

        //8.返回信息
        String token = JwtUtils.createJWTByUserId(user.getUserId());

        return new UserAuthVO(token);
    }

    @Override
    @Transactional
    public Boolean saveNewUser(UserCreateParm param) {
        try {
//            保存用户信息
            this.save(param);
//            保存用户角色信息
            if (param.getRoleIds() != null && !param.getRoleIds().isEmpty()) {
                for (int roleId : param.getRoleIds()) {
                    userRoleService.save(new UserRole(param.getUserId(), roleId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveNewUserForAdmin(UserCreateParm param) {
        param.setPassword("123456");
        return this.saveNewUser(param);
    }

    @Override
    public List<UserVO> queryAllAttendance() {
        List<UserVO> list = userDao.queryAllAttendance();
        list.forEach(this::fillVO);
        return list;
    }

    @Override
    @Transactional
    public boolean updateUser(String id, UserCreateParm param) {
        param.setUserId(id);
        this.updateById(param);

        if (param.getRoleIds() != null) {
            //删除用户角色信息
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
            //保存用户角色信息
            if (param.getRoleIds() != null && !param.getRoleIds().isEmpty()) {
                for (int roleId : param.getRoleIds()) {
                    userRoleService.save(new UserRole(param.getUserId(), roleId));
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteUser(String id) {
        try {
//            删除用户信息
            removeById(id);
//            删除用户角色信息
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Transactional
    @Override
    public boolean unbindWX(String userId) {
        //1.查询用户
        User user = this.lambdaQuery().eq(User::getUserId, userId).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "查无用户信息");
        AssertUtils.notNull(user.getOpenId(), HttpStatus.NOT_FOUND, "用户无绑定微信账号");

        //2.更新账号信息
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getOpenId, null).eq(User::getUserId, userId);
        AssertUtils.isTrue(this.update(updateWrapper), HttpStatus.INTERNAL_SERVER_ERROR, "解绑失败");

        //3.判断是否绑定学生信息
        if (StringUtils.isNotBlank(user.getStudentId())) {
            //更新学生认证信息
            studentService.lambdaUpdate()
                    .set(Student::getIsVerified, false)
                    .eq(Student::getStudentId, user.getStudentId())
                    .update();
        }

        return true;
    }

    @Override
    @Transactional
    public boolean changePassword(String userId, UserChangePasswordDTO param) {
        //1.查询用户
        User user = this.lambdaQuery().eq(User::getUserId, userId).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "查无用户信息");
        //2.校验原密码
        AssertUtils.isTrue(user.getPassword().equals(param.getOldPassword()), HttpStatus.BAD_REQUEST, "原密码错误");
        //3.更新密码
        return this.lambdaUpdate()
                .set(User::getPassword, param.getNewPassword())
                .eq(User::getUserId, userId)
                .update();
    }

    @Override
    @Transactional
    public boolean resetPassword(UserResetPasswordDTO param) {
        //1.查询用户
        User user = this.lambdaQuery().eq(User::getUserId, param.getUserId()).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "查无用户信息");
        //2.更新密码
        return this.lambdaUpdate()
                .set(User::getPassword, param.getNewPassword())
                .eq(User::getUserId, user.getUserId())
                .update();

    }


}

