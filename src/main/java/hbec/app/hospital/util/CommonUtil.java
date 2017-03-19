package hbec.app.hospital.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonUtil {
    // 第三方用户唯一凭证密钥
    // JSAPI接口中获取openid，审核后在公众平台开启开发模式后可查看
    private static String appSecret = "7dc4e623efe5de6f79591546096553a9";
    // 用户信息验证的servlet的地址
    private static String oauthUrl = "http://weixin.niurr.com/oauthServlet";
    // 修改密码信息验证的servlet的地址
    private static String oauthPwdUrl = "http://weixin.niurr.com/oauthPwdServlet";
    // 微信支付的servlet的地址
    private static String payOAuthServlet = "http://weixin.niurr.com/payOAuthServlet";
    // 乐买跳转地址
    // private static String luckyBuyServlet = "http://weixin.niurr.com/luckybuy/luckyBuyServlet";
    private static String luckyBuyServlet = "http://weixin.niurr.com";

    // 第三方用户唯一凭证密钥
    private static String apiSecret = "7dc4e623efe5de6f79591546096553a9";

    public static String getAppSecret() {
        return appSecret;
    }

    public static void setAppSecret(String appSecret) {
        CommonUtil.appSecret = appSecret;
    }

    public static String getOauthUrl() {
        return oauthUrl;
    }

    public static void setOauthUrl(String oauthUrl) {
        CommonUtil.oauthUrl = oauthUrl;
    }

    public static String getOauthPwdUrl() {
        return oauthPwdUrl;
    }

    public static void setOauthPwdUrl(String oauthPwdUrl) {
        CommonUtil.oauthPwdUrl = oauthPwdUrl;
    }

    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getApiSecret() {
        return apiSecret;
    }

    public static void setApiSecret(String apiSecret) {
        CommonUtil.apiSecret = apiSecret;
    }

    public static String getPayOAuthServlet() {
        return payOAuthServlet;
    }

    public static void setPayOAuthServlet(String payOAuthServlet) {
        CommonUtil.payOAuthServlet = payOAuthServlet;
    }

    public static String getLuckyBuyServlet() {
        return luckyBuyServlet;
    }

    public static void setLuckyBuyServlet(String luckyBuyServlet) {
        CommonUtil.luckyBuyServlet = luckyBuyServlet;
    }

}
