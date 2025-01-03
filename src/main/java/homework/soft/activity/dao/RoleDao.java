package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.dto.RoleQuery;
import homework.soft.activity.entity.vo.RoleVO;

/**
 * 角色(Role)表数据库访问层
 *
 * @author jscomet
 * @since 2024-11-24 13:14:05
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    RoleVO queryById(Integer roleId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<RoleVO> queryAll(RoleQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(RoleQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<Role> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<Role> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<Role> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<Role> entities);

    /**
     * 根据用户id获取角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> queryByUserId(@Param("userId") String userId);
}




