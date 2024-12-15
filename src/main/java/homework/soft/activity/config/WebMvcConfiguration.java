package homework.soft.activity.config;

import homework.soft.activity.interceptor.JwtLoginInterceptor;
import homework.soft.activity.interceptor.PermissionAuthorizeInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，注册web层相关组件
 */
@Component
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private JwtLoginInterceptor jwtLoginInterceptor;
    @Resource
    private PermissionAuthorizeInterceptor permissionAuthorizeInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //先根据jwt获取当前用户身份，再对接口的权限进行校验
        registry.addInterceptor(jwtLoginInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(permissionAuthorizeInterceptor)
                .addPathPatterns("/**");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //C:\Users\30597\.m2\repository\org\webjars\swagger-ui\5.10.3\swagger-ui-5.10.3.jar!\META-INF\resources\webjars\swagger-ui\5.10.3
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }

}
