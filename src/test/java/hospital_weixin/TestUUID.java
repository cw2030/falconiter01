package hospital_weixin;

import hbec.platform.commons.utils.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TestUUID {

	public static void main(String[] args) {
		Map<String,String> reuslt = new HashMap<>();
		reuslt.put("result", "0");
		reuslt.put("path", "http://ww.wtes.com");
		SerializeConfig config = new SerializeConfig();
		config.config(Map.class, SerializerFeature.QuoteFieldNames, false);
//		SerializerFeature.config(1, SerializerFeature.QuoteFieldNames, false);
		System.out.println(JSON.toJSONString(reuslt,config));
		System.out.println(JSON.toJSONString(reuslt,Feature.config(0, Feature.AllowUnQuotedFieldNames, true)));
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(uuid);
		
		String fileName = "abcd.jpeg";
		String extendName = ".jpg";
        if(Strings.isNotEmpty(fileName)){
            int idx = 0;
            if((idx = fileName.lastIndexOf(".")) != -1){
                extendName = fileName.substring(idx);
            }
        }
        
        
        System.out.println(extendName);
        
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());
	}

}
