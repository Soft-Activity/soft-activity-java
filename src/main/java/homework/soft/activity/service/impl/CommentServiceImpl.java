package homework.soft.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.soft.activity.dao.CommentDao;
import homework.soft.activity.entity.po.Activity;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.service.CommentService;
import homework.soft.activity.entity.dto.CommentQuery;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.vo.CommentVO;
import homework.soft.activity.service.RegistrationService;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AssertUtils;
import org.springframework.http.HttpStatus;
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
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @Resource
    private RegistrationService registrationService;

    @Override
    public CommentVO queryById(Integer commentId) {
        CommentVO vo = commentDao.queryById(commentId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<CommentVO> queryAll(int current, int pageSize, CommentQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<CommentVO> list = commentDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(CommentVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getStudentId() != null) {
            vo.setStudent(userService.queryById(vo.getStudentId()));
            Activity activity = activityService.lambdaQuery()
                    .select(Activity::getName)
                    .eq(Activity::getActivityId, vo.getActivityId())
                    .one();
            vo.setActivityName(activity != null ? activity.getName() : null);
        }
    }


    @Override
    public int count(CommentQuery param) {
        return commentDao.count(param);
    }

    @Override
    public int getMaxCommentId() {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MAX(comment_id) as comment_id");
        Comment comment = commentDao.selectOne(queryWrapper);
        return comment != null ? comment.getCommentId() : 0;
    }

    @Override
    public Boolean isComment(String userId, Integer activityId) {
        return this.lambdaQuery().eq(Comment::getStudentId, userId).eq(Comment::getActivityId, activityId).count() > 0;
    }
}

