package homework.soft.activity.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.CommentDao;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.service.CommentService;
import homework.soft.activity.entity.dto.CommentQuery;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.vo.CommentVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * (Comment)表服务实现类
 *
 * @author jscomet
 * @since 2024-11-24 14:59:32
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Override
    public CommentVO queryById(Integer commentId) {
        return commentDao.queryById(commentId);
    }

    @Override
    public List<CommentVO> queryAll(int current, int pageSize, CommentQuery param) {
        PageHelper.startPage(current, pageSize);
        return commentDao.queryAll(param);
    }

    @Override
    public int count(CommentQuery param) {
        return commentDao.count(param);
    }
}

