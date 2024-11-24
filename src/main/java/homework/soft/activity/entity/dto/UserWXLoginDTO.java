package homework.soft.activity.entity.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户账号密码登录
 *
 * @author jscomet
 * @since 2024-11-24 11:31:21
 */
@Data
public class UserWXLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1278238911L;
    private String code;
}
