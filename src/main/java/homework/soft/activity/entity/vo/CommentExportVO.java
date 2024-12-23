package homework.soft.activity.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-23 17:15
 */
@Data
public class CommentExportVO {

    @Schema(description = "学号")
    @Excel(name = "学号",width = 20)
    private String schoolId;
    @Schema(description = "用户名")
    @Excel(name = "用户名",width = 20)
    private String userName;
    @Schema(description = "学院名称")
    @Excel(name = "学院名称",width = 35)
    private String collegeName;
    @Schema(description = "评论名称")
    @Excel(name = "评论内容",width = 35)
    private String content;
    @Schema(description = "打卡状态")
    @Excel(name="评分",width = 20)
    public Integer rating;
    @Schema(description = "评论时间")
    @Excel(name = "评论时间" ,width = 35, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static CommentExportVO of(CommentVO commentVO) {
        CommentExportVO exportVO = new CommentExportVO();
        exportVO.setSchoolId(commentVO.getStudent().getStudentId());
        exportVO.setUserName(commentVO.getStudent().getName());
        exportVO.setCollegeName(commentVO.getStudent().getCollege());
        exportVO.setContent(commentVO.getContent());
        exportVO.setRating(commentVO.getRating());
        exportVO.setCreateTime(commentVO.getCreateTime());
        return exportVO;
    }


}
