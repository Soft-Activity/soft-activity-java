package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.RegistrationDao;
import homework.soft.activity.entity.dto.ActivityCheckInParam;
import homework.soft.activity.entity.po.*;
import homework.soft.activity.entity.vo.ActivityVO;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.service.ActivityLocationService;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.RegistrationVO;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.MapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Resource
    private ActivityLocationService activityLocationService;
    @Resource
    private UserService userService;

    @Override
    public RegistrationVO queryById(Integer registrationId) {
        RegistrationVO vo = registrationDao.queryById(registrationId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<RegistrationVO> queryAll(int current, int pageSize, RegistrationQuery param) {
        if(current>=0 && pageSize>=0) {
            PageHelper.startPage(current, pageSize);
        }
        List<RegistrationVO> list = registrationDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(RegistrationVO vo) {
        if(vo==null){
            return;
        }
        if(vo.getStudentId()!=null){
            UserVO user = userService.queryById(vo.getStudentId());
            vo.setCollegeName(user.getCollege());
            vo.setUserName(user.getName());
            vo.setSchoolId(user.getStudentId());
        }
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

    public boolean isRegister(String userId, Integer activityId) {
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        queryWrapper.eq("activity_id", activityId);
        queryWrapper.eq("status", Registration.Status.REGISTERED.getValue());
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
                .set(Registration::getStatus, Registration.Status.CANCELED.getValue())
                .update();
    }

    @Override
    public int getRegistrationCount(Integer activityId) {
//        使用mybaits plus
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id", activityId);
        queryWrapper.eq("status", 0);
        return Math.toIntExact(registrationDao.selectCount(queryWrapper));
    }

    @Override
    @Transactional
    public boolean checkInActivity(String userId, ActivityCheckInParam param) {
        //获取活动信息
        Activity activity = activityService.lambdaQuery().eq(Activity::getActivityId, param.getActivityId()).one();
        AssertUtils.notNull(activity, HttpStatus.BAD_REQUEST, "活动不存在");
        //判断活动状态
        Registration registration = this.lambdaQuery()
                .eq(Registration::getStudentId, userId)
                .eq(Registration::getActivityId, param.getActivityId())
                .eq(Registration::getStatus, Registration.Status.REGISTERED.getValue())
                .one();
        AssertUtils.notNull(registration, HttpStatus.BAD_REQUEST, "未报名该活动");
        //判断是否已经打卡
        AssertUtils.isTrue(!registration.getIsCheckIn(), HttpStatus.BAD_REQUEST, "已打卡");

        //判断是否在打卡时间内
        AssertUtils.isTrue(LocalDateTime.now().isAfter(activity.getCheckInStartTime()) && LocalDateTime.now().isBefore(activity.getCheckInEndTime()), HttpStatus.BAD_REQUEST, "不在打卡时间内");
        MapUtils.Point baiduPoint = MapUtils.Gcj02ToBd09(param.getGcj02Latitude(), param.getGcj02Longitude());
        //判断是否在打卡范围内
        AssertUtils.isTrue(activityLocationService.isInLocation(activity.getCheckInLocationId(), activity.getCheckInRadius(), baiduPoint.getLat(), baiduPoint.getLng()), HttpStatus.BAD_REQUEST, "不在打卡范围内");

        //更新打卡状态
        return this.lambdaUpdate().eq(Registration::getRegistrationId, registration.getRegistrationId())
                .set(Registration::getIsCheckIn, true)
                .set(Registration::getCheckInTime, LocalDateTime.now())
                .set(Registration::getCheckInLat, baiduPoint.getLat())
                .set(Registration::getCheckInLng, baiduPoint.getLng())
                .update();
    }

    @Override
    public int getCheckInCount(Integer activityId) {
        return this.lambdaQuery()
                        .eq(Registration::getActivityId, activityId)
                        .eq(Registration::getIsCheckIn, true)
                        .count().intValue();
    }

    @Override
    public boolean isCheckIn(String userId, Integer activityId) {
        Registration registration = this.lambdaQuery()
                .eq(Registration::getStudentId, userId)
                .eq(Registration::getActivityId, activityId)
                .eq(Registration::getStatus, Registration.Status.REGISTERED.getValue())
                .one();
        AssertUtils.notNull(registration, HttpStatus.BAD_REQUEST, "未报名该活动");

        return registration.getIsCheckIn();
    }

    @Override
    @Transactional
    public boolean registerActivity(String userId, Integer activityId) {
        //获取活动信息
        ActivityVO activity = activityService.queryById(activityId);
        AssertUtils.notNull(activity, HttpStatus.BAD_REQUEST, "活动不存在");

        //判断活动状态
        AssertUtils.isTrue(activity.getStatus() == Registration.Status.REGISTERED.getValue(), HttpStatus.BAD_REQUEST, "活动报名已结束");

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
        registration.setStatus(Registration.Status.REGISTERED.getValue());
        Registration old = this.lambdaQuery().eq(Registration::getStudentId, userId).eq(Registration::getActivityId, activityId).one();
        if (old != null) {
            registration.setRegistrationId(old.getRegistrationId());
            return this.updateById(registration);
        } else {
            return this.save(registration);
        }
    }
}

