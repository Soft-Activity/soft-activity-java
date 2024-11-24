package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.ActivityCategoryDao;
import homework.soft.activity.entity.po.ActivityCategory;
import homework.soft.activity.service.ActivityCategoryService;
import homework.soft.activity.entity.dto.ActivityCategoryQuery;
import homework.soft.activity.entity.po.ActivityCategory;
import homework.soft.activity.entity.vo.ActivityCategoryVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 活动分类表(ActivityCategory)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:12
 */
@Service("activityCategoryService")
public class ActivityCategoryServiceImpl extends ServiceImpl<ActivityCategoryDao, ActivityCategory> implements ActivityCategoryService {
    @Resource
    private ActivityCategoryDao activityCategoryDao;

    @Override
    public ActivityCategoryVO queryById(Integer categoryId) {
        return activityCategoryDao.queryById(categoryId);
    }

    @Override
    public List<ActivityCategoryVO> queryAll(int current, int pageSize, ActivityCategoryQuery param) {
        PageHelper.startPage(current, pageSize);
        return activityCategoryDao.queryAll(param);
    }

    @Override
    public int count(ActivityCategoryQuery param) {
        return activityCategoryDao.count(param);
    }
}

