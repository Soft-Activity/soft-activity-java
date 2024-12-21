package homework.soft.activity.dao;

import homework.soft.activity.entity.dto.ActivityAiReviewCreateParam;
import homework.soft.activity.entity.dto.ActivityAiReviewQuery;
import homework.soft.activity.entity.po.ActivityAiReview;
import homework.soft.activity.entity.vo.ActivityAiReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ActivityAiReview)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-01 22:25:02
 */
@Mapper
public interface ActivityAiReviewDao {
    /**
     * 通过ID查询单条数据
     *
     * @param activityId 主键
     * @return 实例对象
     */
    ActivityAiReviewVO queryById(Integer activityId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<ActivityAiReviewVO> queryAll(ActivityAiReviewQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityAiReviewQuery param);

    /**
     * 新增数据
     *
     * @param param 创建参数
     * @return 影响行数
     */
    int insert(ActivityAiReview param);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ActivityAiReview> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ActivityAiReview> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ActivityAiReview> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ActivityAiReview> entities);

    /**
     * 修改数据
     *
     * @param param 创建参数
     * @return 影响行数
     */
    int update(ActivityAiReviewCreateParam param);

    /**
     * 通过主键删除数据
     *
     * @param activityId 主键
     * @return 影响行数
     */
    int deleteById(Integer activityId);
}
