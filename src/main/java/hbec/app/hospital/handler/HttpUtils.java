package hbec.app.hospital.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.common.io.Files;

public class HttpUtils {

	public static void main(String[] args) {
		String token = "XyS6GnXUFCqvRQ4ixF0dWjsrfvFpHLovi8a_3AQL9xpIa9g2nDb0XUiDh0NkXV7mguCsZvSylbPck-ARWB99AMprrbM0B7hVCrvqNFDx5fWUiytyTz5WHv8hihrh55FyYELdAEAUAH";
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";
		url = url + "?access_token="+ token;
		JSONObject map = new JSONObject();
		map.put("path", "pages/doctorCard/doctorCard");
		map.put("width", 430);
		
		StringEntity entity = new StringEntity(map.toString(),"utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		
		
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					byte[] bytes = EntityUtils.toByteArray(resEntity);
					System.out.println("length-->" + bytes.length);
					Files.write(bytes, new File("d:\\a.jpg"));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
