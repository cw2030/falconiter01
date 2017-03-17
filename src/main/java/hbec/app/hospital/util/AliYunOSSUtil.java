package hbec.app.hospital.util;

import hbec.platform.commons.services.IConfigurationService;
import hbec.platform.commons.utils.Strings;
import hbec.platform.core.ioc.IOC;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.remoting.exchange.Response;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

public class AliYunOSSUtil {
	public static String LOCALPATH = "https://weixin.falconiter.com/files/";
	public static String endpoint = "http://vpc100-oss-cn-hangzhou.aliyuncs.com";
	private static String accessKey = "LTAI5IUF3p3TSsXM";
	private static String secretKey = "rckL32AH6Zky77oku8p2C1xVZYPujE";
	private static String bucket = "medical02";
	private static Logger logger = LoggerFactory.getLogger(AliYunOSSUtil.class);
	private static OSSClient ossClient;
	static {
		try {
			IConfigurationService cfg = IOC.INSTANCE.getBean(IConfigurationService.class);
			if(cfg != null){
				String _endpint = cfg.get("hbec.commons.oss.url");
				if(Strings.isNotEmpty(_endpint)){
					endpoint = _endpint;
				}
			}
			logger.info("[AliYunOSSUtils]endpoint:{}", endpoint);
			ossClient = new OSSClient(endpoint, accessKey,secretKey);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static String upload(byte[] imgs, String filePath) {
		try {
			PutObjectResult  result = ossClient.putObject(bucket, filePath, new ByteArrayInputStream(imgs));
			return result.getETag();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
