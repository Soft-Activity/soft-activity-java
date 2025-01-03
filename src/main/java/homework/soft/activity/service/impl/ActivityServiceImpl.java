package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.ActivityDao;
import homework.soft.activity.entity.dto.CommentQuery;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.ActivityRecentMonthStatVO;
import homework.soft.activity.service.ActivityLocationService;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.entity.dto.ActivityQuery;
import homework.soft.activity.entity.vo.ActivityVO;
import homework.soft.activity.service.CommentService;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.util.AssertUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * (Activity)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 14:58:51
 */
@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, Activity> implements ActivityService {
    @Resource
    private ActivityDao activityDao;

    @Resource
    private CommentService commentService;

    @Resource
    private RegistrationService registrationService;

    @Resource
    private ActivityLocationService activityLocationService;


    @Override
    public ActivityVO queryById(Integer activityId) {
        ActivityVO vo = activityDao.queryById(activityId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<ActivityVO> queryAll(int current, int pageSize, ActivityQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<ActivityVO> list = activityDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(ActivityVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getActivityId() != null) {
            CommentQuery query = new CommentQuery();
            query.setActivityId(vo.getActivityId());
            vo.setRecentComments(commentService.queryAll(1, 3, query));
            vo.setCommentCount(commentService.count(query));
            vo.setCapacity(registrationService.getRegistrationCount(vo.getActivityId()));
            vo.setCheckInCount(registrationService.getCheckInCount(vo.getActivityId()));
            vo.setCheckInLocationName(activityLocationService.getLocationName(vo.getCheckInLocationId()));
        }

    }

    @Override
    public int count(ActivityQuery param) {
        return activityDao.count(param);
    }

    public boolean save(Activity activity) {
        // 检查签到地点
        if (Boolean.TRUE.equals(activity.getIsCheckIn())) {
            AssertUtils.notNull(activity.getCheckInLocationId(), HttpStatus.BAD_REQUEST, "签到地点不能为空");
            AssertUtils.notNull(activity.getCheckInStartTime(), HttpStatus.BAD_REQUEST, "签到开始时间不能为空");
            AssertUtils.notNull(activity.getCheckInEndTime(), HttpStatus.BAD_REQUEST, "签到结束时间不能为空");
        }
        return super.save(activity);
    }

    /**
     * 拿到自增id
     *
     * @return
     */
    @Override
    public int getMaxActivityId() {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MAX(activity_id) as activity_id");
        Activity activity = activityDao.selectOne(queryWrapper);
        return activity != null ? activity.getActivityId() : 0;
    }

    @Override
    public Integer queryCount(Integer categoryId) {
//        使用mybaits-plus的查询方法,一个参数是查询条件，一个参数是查询字段
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        return Math.toIntExact(activityDao.selectCount(queryWrapper));
    }

    @Override
    public List<ActivityRecentMonthStatVO> statisticsRecentMonth() {
        return activityDao.statisticsRecentMonth();
    }
}

