package homework.soft.activity.entity.dto;

import homework.soft.activity.config.valid.QueryGroup;
import homework.soft.activity.config.valid.Sorter;
import homework.soft.activity.config.valid.SorterValidated;
import homework.soft.activity.entity.po.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * (Activity)查询参数
 *
 * @author jscomet
 * @since 2024-11-24 14:58:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityQuery extends Activity {

    @Schema(description = "状态")
    private List<Integer> statuses;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "组织者姓名")
    private String organizerName;

    @Schema(description = "参与学生id")
    private String studentId;

    @Schema(description = "参与学生是否评论")
    private Boolean isStudentComment;

    @Schema(description = "参与学生是否打卡")
    private Boolean isStudentCheckIn;

    @SorterValidated(groups = {QueryGroup.class})
    private Sorter sorter;

}
