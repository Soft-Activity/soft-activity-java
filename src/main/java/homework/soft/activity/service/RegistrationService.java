package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.vo.RegistrationVO;

import java.util.List;

/**
 * 报名表(Registration)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
public interface RegistrationService extends IService<Registration> {
    /**
     * 通过ID查询单条数据
     *
     * @param registrationId 主键
     * @return 实例对象
     */
    RegistrationVO queryById(Integer registrationId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<RegistrationVO> queryAll(int current, int pageSize, RegistrationQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(RegistrationQuery param);

    int getMaxRegistrationId();
    /**
     * 报名活动
     * @param userId 用户id
     * @param activityId 活动id
     * @return 是否成功
     */
    boolean registerActivity(String userId, Integer activityId);
    /**
     * 判断是否报名过
     * @param userId 用户id
     * @param activityId 活动id
     * @return 是否报名过
     */
    Boolean isRegister(String userId, Integer activityId);
    /**
     * 取消报名
     * @param userId 用户id
     * @param activityId 活动id
     * @return 是否成功
     */
    boolean cancelRegister(String userId, Integer activityId);

    /**
     * 查询报名人数
     * @param activityId 活动id
     * @return 报名人数
     */
    int getRegistrationCount(Integer activityId);
}

