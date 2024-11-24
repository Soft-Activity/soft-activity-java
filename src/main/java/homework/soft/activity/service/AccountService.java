package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.Account;
import homework.soft.activity.entity.dto.AccountQuery;
import homework.soft.activity.entity.vo.AccountVO;

import java.util.List;

/**
 * 账号表(Account)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 09:35:29
 */
public interface AccountService extends IService<Account> {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    AccountVO queryById(String userId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<AccountVO> queryAll(int current, int pageSize, AccountQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(AccountQuery param);

}

