package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.ActivityCategory;
import homework.soft.activity.entity.dto.ActivityCategoryQuery;
import homework.soft.activity.entity.vo.ActivityCategoryVO;

import java.util.List;

/**
 * 活动分类表(ActivityCategory)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 14:59:12
 */
public interface ActivityCategoryService extends IService<ActivityCategory> {
    /**
     * 通过ID查询单条数据
     *
     * @param categoryId 主键
     * @return 实例对象
     */
    ActivityCategoryVO queryById(Integer categoryId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<ActivityCategoryVO> queryAll(int current, int pageSize, ActivityCategoryQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityCategoryQuery param);

}

