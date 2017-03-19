package hbec.app.hospital.service.impl;

import hbec.app.hospital.domain.Constants;
import hbec.app.hospital.domain.PaySignRequestHandler;
import hbec.app.hospital.util.CommonUtil;
import hbec.app.hospital.util.Sha1Util;
import hbec.app.hospital.util.XmlUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

/**
 * 支付请求服务
 * 
 * @author yh
 */
public class PayService {
	private static Logger log = LoggerFactory.getLogger(PayService.class);

	/**
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param weChatCode
	 *            微信认证传送的code
	 * @param orderNo
	 *            订单号
	 * @param payAmount
	 *            付款总金额， 单位：元
	 * @param productName
	 *            商品名称
	 * @param productId
	 *            产品id
	 * @param buyNumbers
	 *            购买的具体上证指数点
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public Map<String, String> getOAuthService(String orderNo, int payAmount, String paymentDesc,String openId, String remoteIp)
			throws ServletException, IOException {
		// String state = request.getParameter("state");
		log.info("openId-======" + openId + "===========orderNo======" + orderNo);
		String noncestr = Sha1Util.getNonceStr();// 生成随机字符串
		String timestamp = Sha1Util.getTimeStamp();// 生成1970年到现在的秒数.
		// 付个零时值
		String total_fee = String.valueOf(payAmount * 100);// 100.0
		// 微信金额 以分为单位.这是第二坑.如果不注意.页面的报错.你基本看不出来.因为他提示系统升级,正在维护.扯淡.....
		String out_trade_no = orderNo;// 订单号 state;
		PaySignRequestHandler reqHandler = new PaySignRequestHandler();
		// 初始化 PaySignRequestHandler 类可以在微信的文档中找到.还有相关工具类
		// 执行统一下单接口 获得预支付id
		reqHandler.setParameter("appid", Constants.APPID);
		reqHandler.setParameter("mch_id", Constants.MCHID); // 商户号
		reqHandler.setParameter("nonce_str", noncestr); // 随机字符串
		reqHandler.setParameter("body", paymentDesc); // 商品描述(必填.如果不填.也会提示系统升级.正在维护我艹.)
		reqHandler.setParameter("out_trade_no", out_trade_no); // 商家订单号
		reqHandler.setParameter("total_fee", total_fee); // 商品金额,以分为单位
		reqHandler.setParameter("spbill_create_ip", remoteIp); // 用户的公网ip
																// IpAddressUtil.getIpAddr(request)
		// 下面的notify_url是用户支付成功后为微信调用的action 异步回调.
		reqHandler.setParameter("trade_type", "JSAPI");
		reqHandler.setParameter("notify_url", Constants.PAYBACKURL);
		// ------------需要进行用户授权获取用户openid-------------
		reqHandler.setParameter("openid", openId); // 这个必填.
		// 这里只是在组装数据.还没到执行到统一下单接口.因为统一下单接口的数据传递格式是xml的.所以才需要组装.
		String requestUrl = reqHandler.getRequestURL();// requestUrl 例子:

		/*
		 * <xml><appid>wx2421b1c4370ec43b</appid><attach>支付测试</attach><body>
		 * JSAPI支付测试</body><mch_id>10000100</mch_id><nonce_str>1
		 * add1a30ac87aa2db72f57a2375d8fec
		 * </nonce_str><notify_url>http://wxpay.weixin
		 * .qq.com/pub_v2/pay/notify.v2
		 * .php</notify_url><openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
		 * </openid><out_trade_no
		 * >1415659990</out_trade_no><spbill_create_ip>14.23
		 * .150.211</spbill_create_ip
		 * ><total_fee>1</total_fee><trade_type>JSAPI</
		 * trade_type><sign>0CB01533B8C1EF103065174F50BCA001</sign></xml>
		 */

		// log.info("requestUrl===================" + requestUrl);
		// 统一下单接口提交 xml格式
		URL orderUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
		HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
		conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
		conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
		conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
		conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
		conn.setUseCaches(false); // Post 请求不能使用缓存
		// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
		conn.setRequestProperty("Content-Length", requestUrl.length() + "");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),
				Charsets.UTF_8);
		out.write(requestUrl.toString());
		out.flush();
		out.close();
		String result = getOut(conn);
		 log.info("result=========返回的xml=============" + result);
		Map<String, String> orderMap = new HashMap<String, String>();
		try {
			orderMap = XmlUtil.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		// log.info("orderMap===========================" + orderMap);
		if (orderMap.get("return_code") == null
				|| "FAIL".equals(orderMap.get("return_code"))) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put("error", "错误：连接微信支付接口失败");
			errorMap.put("result", "0");
			return errorMap;
		}

		// 得到的预支付id
		String prepay_id = orderMap.get("prepay_id");
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appId", Constants.APPID);
		params.put("timeStamp", timestamp);
		params.put("nonceStr", noncestr);
		params.put("package", "prepay_id=" + prepay_id);
		params.put("signType", "MD5");

		// 生成支付签名,这个签名 给 微信支付的调用使用
		String paySign = reqHandler.createSignForPay(params);
		Map<String, String> payment = new HashMap<>();
		payment.put("paySign", paySign);
		payment.put("appId", Constants.APPID);
		payment.put("timeStamp", timestamp);
		payment.put("nonceStr", noncestr);
		payment.put("signType", "MD5");
		payment.put("out_trade_no", out_trade_no);
		payment.put("package", "prepay_id=" + prepay_id);
		payment.put("productName", paymentDesc);
		payment.put("payAmount", "" + payAmount);

		System.out.println(payment);
		// return paySign;
		return payment;
	}

	public static String getOut(HttpURLConnection conn) throws IOException {
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			return null;
		}
		// 获取响应内容体
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String line = "";
		StringBuffer strBuf = new StringBuffer();
		while ((line = in.readLine()) != null) {
			strBuf.append(line).append("\n");
		}
		in.close();
		return strBuf.toString().trim();
	}

	public static String SendGET(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果

		try {
			// 创建url
			URL realurl = new URL(url + "?" + param);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
