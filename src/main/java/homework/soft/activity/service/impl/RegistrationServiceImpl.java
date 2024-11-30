package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.RegistrationDao;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.entity.dto.RegistrationQuery;
import homework.soft.activity.entity.po.Registration;
import homework.soft.activity.entity.vo.RegistrationVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 报名表(Registration)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:48
 */
@Service("registrationService")
public class RegistrationServiceImpl extends ServiceImpl<RegistrationDao, Registration> implements RegistrationService {
    @Resource
    private RegistrationDao registrationDao;

    @Override
    public RegistrationVO queryById(Integer registrationId) {
        return registrationDao.queryById(registrationId);
    }

    @Override
    public List<RegistrationVO> queryAll(int current, int pageSize, RegistrationQuery param) {
        PageHelper.startPage(current, pageSize);
        return registrationDao.queryAll(param);
    }

    @Override
    public int count(RegistrationQuery param) {
        return registrationDao.count(param);
    }

    @Override
    public int getMaxRegistrationId() {
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MAX(registration_id) as registration_id");
        Registration registration = registrationDao.selectOne(queryWrapper);
        return registration != null ? registration.getRegistrationId() : 0;
    }
}

