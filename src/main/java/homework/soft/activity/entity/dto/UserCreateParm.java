package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCreateParm extends User {
    @Schema(description = "角色ids")
    private List<Integer> roleIds;
}
