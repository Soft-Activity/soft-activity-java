package homework.soft.activity.entity.dto;

import homework.soft.activity.entity.po.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号表(Account)查询参数
 *
 * @author jscomet
 * @since 2024-11-24 09:35:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountQuery extends Account {
}
