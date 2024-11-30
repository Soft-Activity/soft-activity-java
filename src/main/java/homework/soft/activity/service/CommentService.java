package homework.soft.activity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.entity.dto.CommentQuery;
import homework.soft.activity.entity.vo.CommentVO;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author jscomet
 * @since 2024-11-24 14:59:32
 */
public interface CommentService extends IService<Comment> {
    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    CommentVO queryById(Integer commentId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<CommentVO> queryAll(int current, int pageSize, CommentQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CommentQuery param);

    int getMaxCommentId();
}

