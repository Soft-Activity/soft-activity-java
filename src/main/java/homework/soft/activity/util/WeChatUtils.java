package homework.soft.activity.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import homework.soft.activity.property.AppProperty;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信工具类
 *
 * @author jscomet
 * @since 2024-11-24 18:48
 */
@Slf4j
@Component
public class WeChatUtils {
    @Resource(name = "appProperty")
    private AppProperty _appProperty;

    private static AppProperty appProperty;

    @PostConstruct
    public void init() {
        appProperty = this._appProperty;
    }

    /**
     * 获取用户openId 链接
     */
    private static final String URL_CODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    @Data
    public static class AppletSession {
        private String openid;
        private String unionid;
        private String sessionKey;
        private Integer errCode;
        private String errMsg;
    }

    /**
     * 获取小程序openId
     *
     * @param jsCode 微信登录凭证
     * @return 返回code结果
     */
    public static AppletSession getAppletSessionByCode(String jsCode) {
        AppletSession result = new AppletSession();
        try {
            String url = String.format(URL_CODE2SESSION, appProperty.getApplet().getAppid(), appProperty.getApplet().getSecret(), jsCode);
            String resp = HttpUtil.get(url);
            JSONObject o = JSONObject.parseObject(resp);
            result.setOpenid(o.getString("openid"));
            result.setUnionid(o.getString("unionid"));
            result.setSessionKey(o.getString("session_key"));
            result.setErrCode(o.getInteger("errcode"));
            result.setErrMsg(o.getString("errmsg"));
            log.info("微信小程序 code2session 登录，数据：{}", result);
        } catch (Exception e) {
            log.error("微信小程序 code2session 获取失败，错误：{}", e.getMessage());
            return null;
        }
        return result;
    }

    /**
     * 获取accessToken链接
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static String getAccessToken() {
        //1.构建 url
        String requestUrl = String.format(ACCESS_TOKEN_URL, appProperty.getApplet().getAppid(), appProperty.getApplet().getSecret());

        //2. 发起请求
        String response = HttpUtil.get(requestUrl);

        //3.获得token
        JSONObject result = JSONObject.parseObject(response);
        return (String) result.get("access_token");
    }
}
