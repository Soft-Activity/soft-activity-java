package homework.soft.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import homework.soft.activity.entity.po.UserRole;
import homework.soft.activity.entity.dto.UserRoleQuery;
import homework.soft.activity.entity.vo.UserRoleVO;

/**
 * (UserRole)表数据库访问层
 *
 * @author jscomet
 * @since 2024-11-24 09:26:01
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserRoleVO queryById(String userId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<UserRoleVO> queryAll(UserRoleQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(UserRoleQuery param);

}




