package hbec.app.hospital.handler;

import hbec.app.hospital.domain.Answer;
import hbec.app.hospital.domain.Ask;
import hbec.app.hospital.repository.HospitalRepository;
import hbec.app.hospital.service.IAnswerService;
import hbec.app.hospital.service.IAskService;
import hbec.app.hospital.service.impl.PayService;
import hbec.app.hospital.util.AliYunOSSUtil;
import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.container.HttpParts;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;
import hbec.platform.commons.container.ReadOnlyHttpParams;
import hbec.platform.commons.services.IConfigurationService;
import hbec.platform.commons.utils.Strings;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 提问handler
 * @author work001
 *
 */
public class AskHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AskHandler.class);
	
	@Inject
	private IAskService askService;
	
	@Inject
	private IAnswerService answerService;
	
	@Inject
	private IConfigurationService cfgService;
	
	@Inject
	private HospitalRepository repository;

	/**
	 * 提问请求
	 * @param resp
	 * @param ask
	 */
	@HbecUriHandler(uris="/ask")
	public void ask(IRequest request, IJsonResponse resp){
		try{
			ReadOnlyHttpParams params = request.getParams();
			String[] names = params.getNames();
			for (String name : names) {
				logger.info("key:{},value:{}",name,params.getValue(name));
			}
			HttpParts parts= request.getParts();
			byte[] imgByte = parts.get("img");
			byte[] vedio = parts.get("vedio");
			byte[] imgByte2 = parts.get("img2");
			byte[] imgByte3 = parts.get("img3");
			String img1Str = params.getValue("img1Str");
			String img2Str = params.getValue("img2Str");
			String img3Str = params.getValue("img3Str");
			String vedioStr = params.getValue("vedioStr");
			
			Ask ask = new Ask();
			if(Strings.isNotEmpty(params.getValue("docId"))){
				ask.setDocId(Long.parseLong(params.getValue("docId")));
				ask.setDocOpenId(params.getValue("docOpenId"));
			}
			ask.setOpenId(params.getValue("userOpenId"));
			ask.setAskTitle(params.getValue("title"));
			ask.setAskContent(params.getValue("content"));
			ask.setQuestionTypeId(Long.parseLong(params.getValue("questionTypeId")));
			ask.setQuestionTypeName(params.getValue("questionTypeName"));
			ask.setGwtCreateTime(Clock.systemUTC().millis());
			if(ask.getDocId() <= 0){
				//要所问题的类型随机一个该问题的医生
				List<Map<String,Object>> docList = repository.selectDocFromQuestionTypeName(ask.getQuestionTypeName());
				if(docList.size() > 0){
					if(docList.size() == 1){
						ask.setDocId((Long)docList.get(0).get("id"));
						ask.setDocOpenId((String)docList.get(0).get("open_id"));
					}else{
						ask.setDocId(new Random().nextInt(docList.size()));
					}
				}else{
					logger.warn("[AskCreate]Can't find any doctor.");
				}
			}
			String key = null;
			if(imgByte != null && imgByte.length > 0){
				key = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
				if(Strings.isNotEmpty(AliYunOSSUtil.upload(imgByte, key))){
					ask.setAskImg(AliYunOSSUtil.LOCALPATH + key);
					logger.error("[CreateAsk]{}上传图片1成功：{}",ask.getOpenId(),ask.getAskImg());
				}else{
					logger.error("{}上传图片失败",ask.getOpenId());
				}
			}else{
			    if(Strings.isNotEmpty(img1Str)){
			        ask.setAskImg(img1Str);
			        logger.info("[CreateAsk]{}图片1地址:{}",ask.getOpenId(),img1Str);
			    }else{
			        logger.error("[CreateAsk]{}没有上传图片1",ask.getOpenId());
			    }
				
			}
			if(imgByte2 != null && imgByte2.length > 0){
                key = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                if(Strings.isNotEmpty(AliYunOSSUtil.upload(imgByte2, key))){
                    ask.setAskImg2(AliYunOSSUtil.LOCALPATH + key);
                    logger.error("[CreateAsk]{}上传图片2成功：{}",ask.getOpenId(),ask.getAskImg2());
                }else{
                    logger.error("{}上传图片2失败",ask.getOpenId());
                }
            }else{
                if(Strings.isNotEmpty(img2Str)){
                    ask.setAskImg2(img2Str);
                    logger.info("[CreateAsk]{}图片2地址:{}",ask.getOpenId(),img2Str);
                }else{
                    logger.error("[CreateAsk]{}没有上传图片2",ask.getOpenId());
                }
                
            }
			if(imgByte3 != null && imgByte3.length > 0){
                key = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                if(Strings.isNotEmpty(AliYunOSSUtil.upload(imgByte3, key))){
                    ask.setAskImg3(AliYunOSSUtil.LOCALPATH + key);
                    logger.error("[CreateAsk]{}上传图片成功：{}",ask.getOpenId(),ask.getAskImg3());
                }else{
                    logger.error("{}上传图片失败",ask.getOpenId());
                }
            }else{
                if(Strings.isNotEmpty(img3Str)){
                    logger.info("[CreateAsk]{}图片3地址:{}",ask.getOpenId(),img3Str);
                    ask.setAskImg3(img3Str);
                }else{
                    logger.error("[CreateAsk]{}没有上传图片3",ask.getOpenId());
                }
                
            }
			if(vedio != null && vedio.length > 0){
				key = UUID.randomUUID().toString().replaceAll("-", "") + ".mp4";
				if(Strings.isNotEmpty(AliYunOSSUtil.upload(vedio,key))){
					ask.setAskVedio(AliYunOSSUtil.LOCALPATH + key);
					logger.error("[CreateAsk]{}上传视频成功：{}",ask.getOpenId(),ask.getAskVedio());
				}else{
					logger.error("{}上传视频失败",ask.getOpenId());
				}
			}else{
			    if(Strings.isNotEmpty(vedioStr)){
                    logger.info("[CreateAsk]{}视频地址:{}",ask.getOpenId(),vedioStr);
                    ask.setAskVedio(vedioStr);
                }else{
                    logger.error("[CreateAsk]{}没有上传视频",ask.getOpenId());
                }
			}
			
			askService.save(ask);
			
			PayService ps = new PayService();
			int payAmt = 2;
			String orderNo = "P" + System.nanoTime();
			Map<String,String> result2 = ps.getOAuthService(orderNo, payAmt, "购买医疗问诊服务", ask.getOpenId(), request.getIp());
			if(result2.containsKey("package")){
				askService.saveOrder(ask.getId() + "", orderNo, payAmt);
			}
			resp.setData(result2);
			
		}catch(Exception e){
			logger.error("", e);
			resp.setData("{\"result\":0}");
		}
		
	}
	
	@HbecUriHandler(uris="/ask/{askId}")
	public void queryAsk(IRequest req, IJsonResponse resp){
		String askId = req.getParams().getValue("askId");
		try{
			logger.info("[AskHandler]/ask/{}", askId);
			resp.setData(repository.queryAnswerByAskId(askId));
		}catch(Exception e){
			logger.error("", e);
		}
		
	}
	
}
