package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.dto.ActivityQuery;
import homework.soft.activity.entity.vo.ActivityVO;

/**
 * (Activity)表数据库访问层
 *
 * @author jscomet
 * @since 2024-11-24 14:58:50
 */
@Mapper
public interface ActivityDao extends BaseMapper<Activity> {
    /**
     * 通过ID查询单条数据
     *
     * @param activityId 主键
     * @return 实例对象
     */
    ActivityVO queryById(Integer activityId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<ActivityVO> queryAll(ActivityQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<Activity> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<Activity> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<Activity> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<Activity> entities);

}




