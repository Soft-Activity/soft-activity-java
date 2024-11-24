package homework.soft.activity.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户凭证视图
 *
 * @author jscomet
 * @since 2024-11-24 11:31:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthVO {
    private String token;
}
