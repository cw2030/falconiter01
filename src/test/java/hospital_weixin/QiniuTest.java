package hospital_weixin;

import hbec.app.hospital.util.AliYunOSSUtil;

import java.io.File;

import com.google.common.io.Files;

public class QiniuTest {

	public static void main(String[] args) throws Exception{
		String localFilePath = "d:\\timg.jpg";
		
		String response = AliYunOSSUtil.upload(Files.toByteArray(new File(localFilePath)),"time1.jpg");
		System.out.println(response);
		
		/*localFilePath = "d:\\2.mp4";
		response = AliYunOSSUtil.upload(Files.toByteArray(new File(localFilePath)),"2.mp4");
		System.out.println(response);*/

	}

}
