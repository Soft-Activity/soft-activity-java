package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.ActivityLocation;
import homework.soft.activity.entity.dto.ActivityLocationQuery;
import homework.soft.activity.entity.vo.ActivityLocationVO;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * 活动地点表(ActivityLocation)表服务接口
 *
 * @author jscomet
 * @since 2024-12-21 23:23:34
 */
public interface ActivityLocationService extends IService<ActivityLocation> {
    /**
     * 通过ID查询单条数据
     *
     * @param locationId 主键
     * @return 实例对象
     */
    ActivityLocationVO queryById(Integer locationId);

    /**
     * 查询多条数据
     *
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
     * @return 对象列表
     */
    List<ActivityLocationVO> queryAll(int current, int pageSize, ActivityLocationQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(ActivityLocationQuery param);

    /**
     * 判断是否在活动地点内
     *
     * @param locationId 地址id
     * @param radius     半径
     * @param latitude   纬度
     * @param longitude  经度
     * @return 是否在活动地点内
     */
    boolean isInLocation(Integer locationId, Integer radius, Double latitude, Double longitude);
    /**
     * 获取地点名称
     *
     * @param locationId 地址id
     * @return 地点名称
     */
    String getLocationName(Integer locationId);
}

