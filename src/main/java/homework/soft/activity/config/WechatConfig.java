package homework.soft.activity.config;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import homework.soft.activity.property.AppProperty;
import homework.soft.activity.util.WeChatUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 微信配置
 *
 * @author 30597
 * @since 2024-11-24 18:29
 */
@Data
@Component
@Slf4j
public class WechatConfig {
    @Resource
    private AppProperty appProperty;

    private String appid;
    private String secret;
    private String accessToken;

    @PostConstruct
    public void init() {
        this.appid = appProperty.getApplet().getAppid();
        this.secret = appProperty.getApplet().getSecret();
    }

    /**
     * 每小时轮询
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void fetchToken() {
        String accessToken = WeChatUtils.getAccessToken();
        if (StringUtils.isNotBlank(accessToken)) {
            this.accessToken = accessToken;
        }
    }
}
