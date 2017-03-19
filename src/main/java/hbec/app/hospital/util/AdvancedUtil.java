package hbec.app.hospital.util;

import hbec.app.hospital.domain.WeixinOauth2Token;

import java.awt.List;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

/**
 * 用户授信工具类
 * 
 * @author Yuanyuan
 * 
 */
public class AdvancedUtil {

    private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);

    /**
     * 获取网页授权凭证
     * 
     * @param appId
     * @param appSecret
     * @param code
     * @return WeixinOauth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&"
                + "grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
                log.info(wat.toString());
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errorcode：{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    /**
     * 刷新网页凭证
     * 
     * @param appId
     * @param appSecret
     * @param code
     * @return WeixinOauth2Token
     */
    public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&"
                + "refresh_token=REFRESH_TOKEN";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
        log.info("刷新token请求：" + requestUrl);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {

            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("刷新网页授权凭证失败 errorcode：{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    /**
     * 获取权限获取url
     * 
     * @param appId
     * @param type
     *            请求授权类型（0：base 1: userinfo）
     * @return
     */
    public static String getOauthUrl(String appId, int type, String url) {
        String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&"
                + "response_type=code&scope=snsapi&state=1#wechat_redirect";
        // String servletUrl =
        // CommonUtil.urlEncodeUTF8(CommonUtil.getOauthUrl());
        // String servletUrl = CommonUtil
        // .urlEncodeUTF8("http://weixin.niurr.com/login.jsp");// WeiChatDemo
        // oauthUrl = (type == 0)?oauthUrl.replace("APPID",
        // appId).replace("URL",
        // "http://weixin.niurr.com/login.jsp").replace("snsapi",
        // "snsapi_base"):oauthUrl.replace("APPID", appId).replace("URL",
        // "http://weixin.niurr.com/login.jsp").replace("snsapi",
        // "snsapi_userinfo");
        // http://weixin.niurr.com/login.jsp
        oauthUrl = oauthUrl.replace("APPID", appId).replace("URL", url).replace("snsapi", "snsapi_userinfo");
        return oauthUrl;
    }

    public static String getSign(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        String sign = "";
        Iterator<String> it = params.keySet().iterator();
        ArrayList<String> paraList = new ArrayList<String>();
        while (it.hasNext()) {
            String key;
            // String value;
            key = it.next().toString();
            paraList.add(key);
            // value = params.get(key);
            // System.out.println(key + "--" + value);
        }
        Collections.sort(paraList, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
        String paramStr = "";
        for (String key : paraList) {
            paramStr = paramStr + key + "=" + params.get(key) + "&";
        }
        // if (StringUtils.isNotBlank(paramStr)) {
        // paramStr = paramStr.substring(1); }
        paramStr = paramStr + CommonUtil.getApiSecret();
        try {
            sign = EncoderByMd5(paramStr).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * MD5加密
     * 
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    private static int startNum = 0;

    /**
     * 获取起始号
     * 
     * @return
     */
    public static int getSequence() {
        int start = 0;
        startNum = startNum + 1;
        start = startNum;
        return start;
    }
}
