package homework.soft.activity.interceptor;

import homework.soft.activity.annotation.PermissionAuthorize;
import homework.soft.activity.constant.enums.RoleType;
import homework.soft.activity.exception.HttpErrorException;
import homework.soft.activity.property.AppProperty;
import homework.soft.activity.service.RoleService;
import homework.soft.activity.util.AuthUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PermissionAuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    private RoleService roleService;
    @Resource
    private AppProperty appProperty;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        // 判断是否有注解
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }
        if (!Boolean.TRUE.equals(appProperty.getSecurity().isEnabled())) {
            return true;
        }

        PermissionAuthorize permissionAuthorize = method.getMethod().getAnnotation(PermissionAuthorize.class);
        if (permissionAuthorize == null) {
            return true;
        }

        // 判断是否已登录
        if (!AuthUtils.isAuthenticated()) {
            throw new HttpErrorException(HttpStatus.UNAUTHORIZED, "未登录，无权限");
        }
        // 超级管理员允许所有操作
        if (AuthUtils.hasRole(RoleType.SUPER_ADMIN)) {
            return true;
        }

        //校验是否有该角色的权限
        RoleType[] AuthRoles = permissionAuthorize.value();
        if (AuthUtils.hasAnyRole(AuthRoles)) {
            return true;
        }

        // 权限匹配失败，返回 403
        throw new HttpErrorException(HttpStatus.FORBIDDEN, "无接口访问权限");
    }
}
