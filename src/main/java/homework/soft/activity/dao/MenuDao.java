package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.soft.activity.entity.po.Menu;
import homework.soft.activity.entity.po.Menu;
import homework.soft.activity.entity.dto.MenuQuery;
import homework.soft.activity.entity.vo.MenuVO;
import org.apache.ibatis.annotations.Select;

/**
 * 菜单表(Menu)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-20 15:55:42
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {
    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    Menu queryById(Long menuId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<Menu> queryAll(MenuQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(MenuQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<Menu> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<Menu> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<Menu> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<Menu> entities);


    /**
     * 通过角色IDs查询菜单
     * @param roleIds 角色IDs
     * @return 菜单列表
     */
    List<Menu> selectMenusByRoleIds(@Param("roleIds") List<Integer> roleIds);

}




