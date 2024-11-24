package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.AccountDao;
import homework.soft.activity.entity.po.Account;
import homework.soft.activity.service.AccountService;
import homework.soft.activity.entity.dto.AccountQuery;
import homework.soft.activity.entity.vo.AccountVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 账号表(Account)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 09:35:29
 */
@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {
    @Resource
    private AccountDao accountDao;

    @Override
    public AccountVO queryById(String userId) {
        return accountDao.queryById(userId);
    }

    @Override
    public List<AccountVO> queryAll(int current, int pageSize, AccountQuery param) {
        PageHelper.startPage(current, pageSize);
        return accountDao.queryAll(param);
    }

    @Override
    public int count(AccountQuery param) {
        return accountDao.count(param);
    }
}

