package hospital_weixin;

import java.util.UUID;

import hbec.platform.commons.utils.Strings;

public class TestUUID {

	public static void main(String[] args) {
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
