package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.dto.ActivityQuery;
import homework.soft.activity.entity.vo.ActivityVO;

import java.util.List;

/**
 * (Activity)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 14:58:51
 */
public interface ActivityService extends IService<Activity> {
    /**
     * 通过ID查询单条数据
     *
     * @param activityId 主键
     * @return 实例对象
     */
    ActivityVO queryById(Integer activityId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<ActivityVO> queryAll(int current, int pageSize, ActivityQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityQuery param);


    /**
     * 拿到自增id
     * @return
     */
    int getMaxActivityId();

}

