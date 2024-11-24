package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Comment)查询参数
 *
 * @author jscomet
 * @since 2024-11-24 14:59:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQuery extends Comment {
}
