package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.dto.UserChangePasswordDTO;
import homework.soft.activity.entity.dto.UserCreateParm;
import homework.soft.activity.entity.dto.UserResetPasswordDTO;
import homework.soft.activity.entity.po.Student;
import homework.soft.activity.entity.po.User;
import homework.soft.activity.entity.dto.UserQuery;
import homework.soft.activity.entity.vo.UserAuthVO;
import homework.soft.activity.entity.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户(User)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 11:31:19
 */
public interface UserService extends IService<User> {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserVO queryById(String userId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<UserVO> queryAll(int current, int pageSize, UserQuery param);
    /**
     * 保存新用户
     *
     * @param param 用户创建参数
     * @return 是否保存成功
     */
    Boolean saveNewUser(UserCreateParm param);
    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(UserQuery param);

    /**
     * 用户通过学号密码登录
     *
     * @param studentId 用户ID
     * @param password 用户密码
     * @return 用户认证信息
     */
    UserAuthVO loginByPassword( String studentId, String password);

    /**
     * 用户通过微信登录
     *
     * @param code 微信登录码
     * @return 用户认证信息
     */
    UserAuthVO loginByWX(String code);

    /**
     * 微信用户通过账号密码绑定账号
     *
     * @param code 微信登录码
     * @param studentId 学号/学工号
     * @param password 用户密码
     * @return 用户认证信息
     */
    UserAuthVO bindWxByPassword(String code,String studentId, String password);

    /**
     * 微信用户通过学生信息绑定账号
     *
     * @param code 微信登录码
     * @param student 学生信息
     * @return 用户认证信息
     */
    UserAuthVO bindWxByStudentInfo(String code, Student student);



    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @param param 用户创建参数
     * @return 是否更新成功
     */
    boolean updateUser(String id, UserCreateParm param);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(String id);

    /**
     * 解绑微信
     * @param userId 用户id
     * @return 是否解绑成功
     */
    boolean unbindWX(String userId);
    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param param 修改密码参数
     * @return 是否修改成功
     */
    boolean changePassword(String userId, UserChangePasswordDTO param);
    /**
     * 重置密码
     *
     * @param param 重置密码参数
     * @return 是否重置成功
     */
    boolean resetPassword(UserResetPasswordDTO param);
    /**
     * 保存新用户
     *
     * @param param 用户创建参数
     * @return 是否保存成功
     */
    boolean saveNewUserForAdmin(UserCreateParm param);
    /**
     * 查询全勤用户
     *
     * @return 全勤用户列表
     */
    List<UserVO> queryAllAttendance();
}

