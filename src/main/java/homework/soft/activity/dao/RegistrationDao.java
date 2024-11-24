package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.vo.RegistrationVO;

/**
 * 报名表(Registration)表数据库访问层
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
@Mapper
public interface RegistrationDao extends BaseMapper<Registration> {
    /**
     * 通过ID查询单条数据
     *
     * @param registrationId 主键
     * @return 实例对象
     */
    RegistrationVO queryById(Integer registrationId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<RegistrationVO> queryAll(RegistrationQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(RegistrationQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<Registration> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<Registration> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<Registration> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<Registration> entities);

}




