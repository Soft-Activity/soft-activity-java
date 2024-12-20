package homework.soft.activity.service;

import homework.soft.activity.entity.dto.ActivityAiReviewCreateParam;
import homework.soft.activity.entity.dto.ActivityAiReviewQuery;
import homework.soft.activity.entity.po.ActivityAiReview;
import homework.soft.activity.entity.vo.ActivityAiReviewVO;

import java.util.List;

/**
 * (ActivityAiReview)表服务接口
 *
 * @author makejava
 * @since 2024-12-01 22:25:03
 */
public interface ActivityAiReviewService {
    /**
     * 通过ID查询单条数据
     *
     * @param activityId 主键
     * @return 实例对象
     */
    ActivityAiReviewVO queryById(Integer activityId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<ActivityAiReviewVO> queryAll(int current, int pageSize, ActivityAiReviewQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityAiReviewQuery param);

    /**
     * 新增数据
     *
     * @param param 创建参数
     * @return 是否成功
     */
    boolean insert(ActivityAiReviewCreateParam param);

    /**
     * 修改数据
     *
     * @param activityId 主键
     * @param param 创建参数
     * @return 是否成功
     */
    boolean update(Integer activityId, ActivityAiReviewCreateParam param);

    /**
     * 通过主键删除数据
     *
     * @param activityId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer activityId);

    ActivityAiReviewVO queryByIdCache(Integer id);

    void insertEntry(ActivityAiReview param);
}
