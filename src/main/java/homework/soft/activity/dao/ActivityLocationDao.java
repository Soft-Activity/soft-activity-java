package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.ActivityLocation;
import homework.soft.activity.entity.po.ActivityLocation;
import homework.soft.activity.entity.dto.ActivityLocationQuery;
import homework.soft.activity.entity.vo.ActivityLocationVO;

/**
 * 活动地点表(ActivityLocation)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-21 23:23:34
 */
@Mapper
public interface ActivityLocationDao extends BaseMapper<ActivityLocation> {
    /**
     * 通过ID查询单条数据
     *
     * @param locationId 主键
     * @return 实例对象
     */
    ActivityLocationVO queryById(Integer locationId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<ActivityLocationVO> queryAll(ActivityLocationQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityLocationQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<ActivityLocation> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<ActivityLocation> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<ActivityLocation> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<ActivityLocation> entities);

}




