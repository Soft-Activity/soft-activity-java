package homework.soft.activity.context;

import homework.soft.activity.entity.vo.UserVO;

public class CurrentUserContext {
    public static ThreadLocal<UserVO> currentUser = new ThreadLocal<>();

    public static UserVO getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(UserVO user) {
        currentUser.set(user);
    }

    public static void removeCurrentUser() {
        currentUser.remove();
    }
}
