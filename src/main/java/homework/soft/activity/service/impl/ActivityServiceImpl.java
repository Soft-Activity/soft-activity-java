package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.ActivityDao;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.entity.dto.ActivityQuery;
import homework.soft.activity.entity.vo.ActivityVO;
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

    @Override
    public ActivityVO queryById(Integer activityId) {
        return activityDao.queryById(activityId);
    }

    @Override
    public List<ActivityVO> queryAll(int current, int pageSize, ActivityQuery param) {
        PageHelper.startPage(current, pageSize);
        return activityDao.queryAll(param);
    }

    @Override
    public int count(ActivityQuery param) {
        return activityDao.count();
    }

    /**
     * 拿到自增id
     * @return
     */
    @Override
    public int getMaxActivityId() {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MAX(activity_id) as activity_id");
        Activity activity = activityDao.selectOne(queryWrapper);
        return activity != null ? activity.getActivityId() : null;
    }
}

