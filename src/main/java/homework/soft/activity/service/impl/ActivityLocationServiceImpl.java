package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.ActivityLocationDao;
import homework.soft.activity.entity.po.ActivityLocation;
import homework.soft.activity.exception.HttpErrorException;
import homework.soft.activity.service.ActivityLocationService;
import homework.soft.activity.entity.dto.ActivityLocationQuery;
import homework.soft.activity.entity.po.ActivityLocation;
import homework.soft.activity.entity.vo.ActivityLocationVO;
import homework.soft.activity.util.AssertUtils;
import homework.soft.activity.util.MapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.io.IOException;
import java.util.List;

/**
 * 活动地点表(ActivityLocation)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-21 23:23:34
 */
@Service("activityLocationService")
public class ActivityLocationServiceImpl extends ServiceImpl<ActivityLocationDao, ActivityLocation> implements ActivityLocationService {
    @Resource
    private ActivityLocationDao activityLocationDao;

    @Override
    public ActivityLocationVO queryById(Integer locationId) {
        return activityLocationDao.queryById(locationId);
    }

    @Override
    public List<ActivityLocationVO> queryAll(int current, int pageSize, ActivityLocationQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        return activityLocationDao.queryAll(param);
    }

    @Override
    public int count(ActivityLocationQuery param) {
        return activityLocationDao.count(param);
    }

    @Override
    public boolean isInLocation(Integer locationId, Integer radius, Double latitude, Double longitude) {
        ActivityLocation location = activityLocationDao.selectById(locationId);
        AssertUtils.notNull(location, HttpStatus.NOT_FOUND, "活动地点不存在");

        MapUtils.Point center = MapUtils.Point.builder().lat(location.getLat()).lng(location.getLng()).build();
        MapUtils.Point point = MapUtils.Point.builder().lat(latitude).lng(longitude).build();
        try {
            double distance = MapUtils.getDistance(center, point);
            AssertUtils.isTrue(distance <= radius, HttpStatus.BAD_REQUEST, "现在距离打卡地点为%d米,打卡范围为%d米".formatted((int) distance, radius));
            return true;
        } catch (IOException e) {
            log.error("计算距离失败", e);
            throw new HttpErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "计算距离失败:" + e.getMessage());
        }
    }

    @Override
    public String getLocationName(Integer locationId) {
        ActivityLocation location = activityLocationDao.selectById(locationId);
        return location != null ? location.getName() : null;
    }
}

