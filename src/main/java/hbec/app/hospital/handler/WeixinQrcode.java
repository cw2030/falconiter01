package hbec.app.hospital.handler;

import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.xy.platform.commons.annotations.HbecUriHandler;
import com.xy.platform.commons.container.IJsonResponse;
import com.xy.platform.commons.container.IRequest;
import com.xy.platform.commons.utils.Strings;

import hbec.app.hospital.util.AliYunOSSUtil;

public class WeixinQrcode {

	private static Logger logger = LoggerFactory.getLogger(WeixinQrcode.class);

	@HbecUriHandler(uris="/qrcode")
	public void qrcode(IRequest req, IJsonResponse resp) {
		Map<String, Object> result = Maps.newHashMap();
		
		String token = req.getParams().getValue("token");
		
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";
		url = url + "?access_token=" + token;
		JSONObject json = new JSONObject();
		json.put("path", req.getParams().getValue("path"));
		json.put("width", req.getParams().getValue("width"));
		if(Strings.isEmpty(token) || json.keySet().size() != 2){
			logger.error("[QRCode]token:{},body:{}",token,json);
			result.put("result", "0");
			resp.setData(json);
			return;
		}
		logger.info("[QRCode]token:{},body:{}",token,json);

		StringEntity entity = new StringEntity(json.toString(), "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
			HttpEntity resEntity = response.getEntity();
			/*if (response != null) {
				
				if (resEntity != null) {
					byte[] bytes = EntityUtils.toByteArray(resEntity);
					System.out.println("length-->" + bytes.length);
					Files.write(bytes, new File("d:\\a.jpg"));
				}
			}*/
			String extendName = ".jpg";
			byte[] bytes =  EntityUtils.toByteArray(resEntity);
			
			String key = UUID.randomUUID().toString().replaceAll("-", "")+ extendName;
			if (bytes != null && bytes.length > 0) {
				if (Strings.isNotEmpty(AliYunOSSUtil.upload(bytes, key))) {
					result.put("path", AliYunOSSUtil.LOCALPATH + key);
					result.put("result", "1");

					logger.info("[QRCode1]生成QRCode图片文件成功：{}", key);
				} else {
					result.put("result", "0");
					logger.info("[QRCode2]生成QRCode图片文失败");
				}
			}else{
				result.put("result", "0");
				logger.info("[QRCode3]没有收到二维码图片Data");
			}
			logger.info("[uploadImage]: {}", result);
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
					httpPost.completed();
					httpPost = null;
					httpClient = null;
				} catch (Exception ex) {
					logger.error("", ex);
				}
			}
		}
		resp.setData(result);
	}
}