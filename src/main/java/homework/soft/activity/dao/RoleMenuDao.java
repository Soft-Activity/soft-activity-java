package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.RoleMenu;
import homework.soft.activity.entity.po.RoleMenu;
import homework.soft.activity.entity.dto.RoleMenuQuery;
import homework.soft.activity.entity.vo.RoleMenuVO;

/**
 * 角色-菜单关联表(RoleMenu)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-20 15:56:09
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleMenuVO queryById(Long id);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<RoleMenuVO> queryAll(RoleMenuQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(RoleMenuQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<RoleMenu> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<RoleMenu> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<RoleMenu> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<RoleMenu> entities);

}




