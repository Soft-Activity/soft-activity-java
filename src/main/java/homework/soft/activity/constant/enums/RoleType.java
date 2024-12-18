package homework.soft.activity.constant.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    /**
     * 超级管理员
     */
    SUPER_ADMIN("超级管理员", "SUPER_ADMIN", 1),
    /**
     * 老师
     */
    TEACHER("老师", "TEACHER", 2),
    /**
     * 学生
     */
    STUDENT("学生", "STUDENT", 3),
    ;
    private final String cnName;
    private final String enName;
    private final Integer roleId;


    RoleType(String cnName, String enName, Integer roleId) {
        this.cnName = cnName;
        this.enName = enName;
        this.roleId = roleId;
    }

    public static final RoleType[] values = values();
}
