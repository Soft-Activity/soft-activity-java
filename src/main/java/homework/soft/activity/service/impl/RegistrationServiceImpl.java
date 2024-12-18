package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.RegistrationDao;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.ActivityVO;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.RegistrationVO;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报名表(Registration)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
@Service("registrationService")
public class RegistrationServiceImpl extends ServiceImpl<RegistrationDao, Registration> implements RegistrationService {
    @Resource
    private RegistrationDao registrationDao;
    @Resource
    private ActivityService activityService;

    @Override
    public RegistrationVO queryById(Integer registrationId) {
        return registrationDao.queryById(registrationId);
    }

    @Override
    public List<RegistrationVO> queryAll(int current, int pageSize, RegistrationQuery param) {
        PageHelper.startPage(current, pageSize);
        return registrationDao.queryAll(param);
    }

    @Override
    public int count(RegistrationQuery param) {
        return registrationDao.count(param);
    }

    @Override
    public int getMaxRegistrationId() {
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MAX(registration_id) as registration_id");
        Registration registration = registrationDao.selectOne(queryWrapper);
        return registration != null ? registration.getRegistrationId() : 0;
    }

    public Boolean isRegister(String userId, Integer activityId) {
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        queryWrapper.eq("activity_id", activityId);
        queryWrapper.eq("status", 0);
        return registrationDao.selectCount(queryWrapper) > 0;
    }

    @Override
    @Transactional
    public boolean cancelRegister(String userId, Integer activityId) {
        //获取活动信息
        ActivityVO activity = activityService.queryById(activityId);
        AssertUtils.notNull(activity, HttpStatus.BAD_REQUEST, "活动不存在");

        //判断活动状态
        AssertUtils.isTrue(activity.getStatus() == 0, HttpStatus.BAD_REQUEST, "不能取消报名");

        // 更新活动人数
        activityService.lambdaUpdate().eq(Activity::getActivityId, activityId)
                .setSql("capacity = capacity - 1")
                .update();

        //判断是否报名
        AssertUtils.isTrue(this.isRegister(userId, activityId), HttpStatus.BAD_REQUEST, "未报名该活动");
        //取消报名
        return this.lambdaUpdate()
                .eq(Registration::getStudentId, userId)
                .eq(Registration::getActivityId, activityId)
                .set(Registration::getStatus, 1)
                .update();
    }

    @Override
    @Transactional
    public boolean registerActivity(String userId, Integer activityId) {
        //获取活动信息
        ActivityVO activity = activityService.queryById(activityId);
        AssertUtils.notNull(activity, HttpStatus.BAD_REQUEST, "活动不存在");

        //判断活动状态
        AssertUtils.isTrue(activity.getStatus() == 0, HttpStatus.BAD_REQUEST, "活动报名已结束");

        //判断活动人数
        AssertUtils.isTrue(activity.getCapacity() <= activity.getMaxCapacity(), HttpStatus.BAD_REQUEST, "活动人数已满");

        //判断是否报名
        AssertUtils.isTrue(!this.isRegister(userId, activityId), HttpStatus.BAD_REQUEST, "已报名该活动");

        // 更新活动人数
        activityService.lambdaUpdate().eq(Activity::getActivityId, activityId)
                .setSql("capacity = capacity + 1")
                .update();

        //构建参数
        Registration registration = new Registration();
        registration.setStudentId(userId);
        registration.setActivityId(activityId);
        registration.setStatus(0);
        Registration old = this.lambdaQuery().eq(Registration::getStudentId, userId).eq(Registration::getActivityId, activityId).one();
        if (old != null) {
            registration.setRegistrationId(old.getRegistrationId());
            return this.updateById(registration);
        } else {
            return this.save(registration);
        }
    }
}

