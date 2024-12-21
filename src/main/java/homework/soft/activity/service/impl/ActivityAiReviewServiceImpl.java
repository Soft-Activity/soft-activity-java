package homework.soft.activity.service.impl;

import homework.soft.activity.entity.dto.ActivityAiReviewCreateParam;
import homework.soft.activity.entity.dto.ActivityAiReviewQuery;
import homework.soft.activity.entity.po.ActivityAiReview;
import homework.soft.activity.entity.vo.ActivityAiReviewVO;
import homework.soft.activity.dao.ActivityAiReviewDao;
import homework.soft.activity.service.ActivityAiReviewService;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * (ActivityAiReview)表服务实现类
 *
 * @author makejava
 * @since 2024-12-01 22:25:04
 */
@Service("activityAiReviewService")
public class ActivityAiReviewServiceImpl implements ActivityAiReviewService {
    @Resource
    private ActivityAiReviewDao activityAiReviewDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ActivityAiReviewVO queryById(Integer activityId) {
        return activityAiReviewDao.queryById(activityId);
    }

    @Override
    public List<ActivityAiReviewVO> queryAll(int current, int pageSize, ActivityAiReviewQuery param) {
        PageHelper.startPage(current, pageSize);
        return activityAiReviewDao.queryAll(param);
    }

    @Override
    public int count(ActivityAiReviewQuery param) {
        return activityAiReviewDao.count(param);
    }

    @Override
    public boolean insert(ActivityAiReviewCreateParam param) {
        return activityAiReviewDao.insert(param) > 0;
    }

    @Override
    public boolean update(Integer activityId, ActivityAiReviewCreateParam param) {
        param.setActivityId(activityId);
        return activityAiReviewDao.update(param) > 0;
    }

    @Override
    public boolean deleteById(Integer activityId) {
        return activityAiReviewDao.deleteById(activityId) > 0;
    }

    @Override
    public ActivityAiReviewVO queryByIdCache(Integer id) {
        String cacheKey = "activityAiReview:" + id;
        if (!redisTemplate.hasKey(cacheKey)) {
            ActivityAiReviewVO review = queryById(id);
            if (review != null) {
                Map<String, Object> reviewMap = new HashMap<>();
                reviewMap.put("activityId", review.getActivityId());
                reviewMap.put("aiAnalysis", review.getAiAnalysis());
                reviewMap.put("goodNum", review.getGoodNum());
                reviewMap.put("mediumNum", review.getMediumNum());
                reviewMap.put("poorNum", review.getPoorNum());
                reviewMap.put("averageScore", review.getAverageScore());
                
                redisTemplate.opsForHash().putAll(cacheKey, reviewMap);
            }
            return review;
        }

        Map<Object, Object> reviewMap = redisTemplate.opsForHash().entries(cacheKey);
        if (reviewMap.isEmpty()) {
            return null;
        }

        ActivityAiReviewVO activityAiReviewVO = new ActivityAiReviewVO();
        activityAiReviewVO.setActivityId((Integer) reviewMap.get("activityId"));
        activityAiReviewVO.setAiAnalysis((String) reviewMap.get("aiAnalysis"));
        activityAiReviewVO.setGoodNum((Integer) reviewMap.get("goodNum"));
        activityAiReviewVO.setMediumNum((Integer) reviewMap.get("mediumNum"));
        activityAiReviewVO.setPoorNum((Integer) reviewMap.get("poorNum"));
        activityAiReviewVO.setAverageScore((Double) reviewMap.get("averageScore"));

        return activityAiReviewVO;
    }

    @Override
    public void insertEntry(ActivityAiReview param) {
        activityAiReviewDao.insert(param);
    }
}
