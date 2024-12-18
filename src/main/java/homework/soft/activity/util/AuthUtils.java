package homework.soft.activity.util;


import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.context.CurrentUserContext;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.vo.UserVO;


public class AuthUtils {

    public static UserVO getUserDetails() {
        return CurrentUserContext.getCurrentUser();
    }

    public static String getCurrentUserId() {
        return getUserDetails().getUserId();
    }

    public static void setUserDetails(UserVO user) {
        CurrentUserContext.setCurrentUser(user);
    }

    public static void removeUserDetails() {
        CurrentUserContext.removeCurrentUser();
    }

    /**
     * 用户是否登录
     *
     * @return 是否
     */
    public static boolean isAuthenticated() {
        return getUserDetails() != null && getUserDetails().getUserId() != null;
    }

    public static boolean hasRole(RoleType type) {
        return hasRole(type.name());
    }

    public static boolean hasRole(String authority) {
        UserVO userDetails = getUserDetails();
        return userDetails.getRoles().stream().map(Role::getEname).anyMatch(authority::equals);
    }

    public static boolean hasAnyRole(RoleType... types) {
        for (RoleType type : types) {
            if (hasRole(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnyRole(String... authorities) {
        for (String authority : authorities) {
            if (hasRole(authority)) {
                return true;
            }
        }
        return false;
    }

}
