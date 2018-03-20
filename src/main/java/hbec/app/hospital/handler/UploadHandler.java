package hbec.app.hospital.handler;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.xy.platform.commons.annotations.HbecUriHandler;
import com.xy.platform.commons.container.IJsonResponse;
import com.xy.platform.commons.container.IRequest;
import com.xy.platform.commons.utils.Strings;

import hbec.app.hospital.util.AliYunOSSUtil;

public class UploadHandler {
    private static Logger logger = LoggerFactory.getLogger(UploadHandler.class);

    @HbecUriHandler(uris="/up/image")
    public void uploadImg(IRequest req, IJsonResponse resp){
        String fileName = req.getParams().getValue("fileName");
        String extendName = ".jpg";
        if(Strings.isNotEmpty(fileName)){
            int idx = 0;
            if((idx = fileName.lastIndexOf(".")) != -1){
                extendName = fileName.substring(idx);
            }
        }
        String openId = req.getParams().getValue("openId");
        byte[] bytes = req.getParts().get("file");
        Map<String,Object> result = Maps.newHashMap();
        String key = UUID.randomUUID().toString().replaceAll("-", "") + extendName;
        if(bytes != null && bytes.length > 0){
            if(Strings.isNotEmpty(AliYunOSSUtil.upload(bytes,key))){
                result.put("path", AliYunOSSUtil.LOCALPATH + key);
                result.put("result", "1");
                
                logger.info("[CreateAsk]{}上传图片文件成功：{}",openId,fileName);
            }else{
                result.put("result", "0");
                logger.info("{}上传图片文件[{}]失败",openId,fileName);
            }
        }else{
            logger.error("{}没有收到上传图片文件[{}]的内容",openId,fileName);
        }
        
        logger.info("[uploadImage]: {}", result);
        resp.setData(result);
    }
    
    @HbecUriHandler(uris="/up/vedio")
    public void uploadVedio(IRequest req, IJsonResponse resp){
        String fileName = req.getParams().getValue("fileName");
        String extendName = ".mp4";
        if(Strings.isNotEmpty(fileName)){
            int idx = 0;
            if((idx = fileName.lastIndexOf(".")) != -1){
                extendName = fileName.substring(idx);
            }
        }
        String openId = req.getParams().getValue("openId");
        byte[] bytes = req.getParts().get("file");
        Map<String,Object> result = Maps.newHashMap();
        String key = UUID.randomUUID().toString().replaceAll("-", "") + extendName;
        if(bytes != null && bytes.length > 0){
            if(Strings.isNotEmpty(AliYunOSSUtil.upload(bytes,key))){
                result.put("path", AliYunOSSUtil.LOCALPATH + key);
                result.put("result", "1");
                
                logger.info("[CreateAsk]{}上传视频文件成功：{}",openId,fileName);
            }else{
                result.put("result", "0");
                logger.error("{}上传视频文件[{}]失败",openId,fileName);
            }
        }else{
            logger.info("{}没有收到上传视频文件[{}]的内容",openId,fileName);
        }
        logger.info("[uploadVedio]: {}", result);
        resp.setData(result);
    }
    
    @HbecUriHandler(uris="/up/voice")
    public void uploadvoice(IRequest req, IJsonResponse resp){
        String fileName = req.getParams().getValue("fileName");
        String extendName = ".mp3";
        if(Strings.isNotEmpty(fileName)){
            int idx = 0;
            if((idx = fileName.lastIndexOf(".")) != -1){
                extendName = fileName.substring(idx);
            }
        }
        String openId = req.getParams().getValue("openId");
        byte[] bytes = req.getParts().get("file");
        Map<String,Object> result = Maps.newHashMap();
        String key = UUID.randomUUID().toString().replaceAll("-", "") + extendName;
        if(bytes != null && bytes.length > 0){
            if(Strings.isNotEmpty(AliYunOSSUtil.upload(bytes,key))){
                result.put("path", AliYunOSSUtil.LOCALPATH + key);
                result.put("result", "1");
                
                logger.error("[CreateAsk]{}上传视频文件成功：{}",openId,fileName);
            }else{
                result.put("result", "0");
                logger.error("{}上传视频文件[{}]失败",openId,fileName);
            }
        }else{
            logger.error("{}没有收到上传视频文件[{}]的内容",openId,fileName);
        }
        
        logger.info("[uploadvoice]: {}", result);
        resp.setData(result);
    }
}
