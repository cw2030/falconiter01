package hbec.app.hospital.handler;

import java.time.Clock;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.platform.commons.annotations.HbecUriHandler;
import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.annotations.RequestMethod;
import com.xy.platform.commons.container.HttpParts;
import com.xy.platform.commons.container.IJsonResponse;
import com.xy.platform.commons.container.IRequest;
import com.xy.platform.commons.container.ReadOnlyHttpParams;
import com.xy.platform.commons.utils.Strings;

import hbec.app.hospital.domain.Answer;
import hbec.app.hospital.repository.HospitalRepository;
import hbec.app.hospital.util.AliYunOSSUtil;

public class AnswerHandler {
	
	@Inject
	private HospitalRepository repository;
	
	private static Logger logger = LoggerFactory.getLogger(AnswerHandler.class);

	/**
	 * 要所问题ID显示该问题的医生回
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris={"/answer/{askId}"})
	public void queryAnswer(IRequest req, IJsonResponse resp){
		resp.setData(repository.queryAnswerByAskId(req.getParams().getValue("askId")));
	}
	
	/**
	 * 创建回答
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris={"/answer"}, method=RequestMethod.POST)
	public void answer(IRequest req, IJsonResponse resp){
		Map<String,String> map = new HashMap<>();
		ReadOnlyHttpParams params = req.getParams();
		Arrays.asList(params.getNames()).forEach(key -> {
			logger.info("[Ask]key:{},value:{}",key,params.getValue(key));
		});
		String askId = params.getValue("askId");
		String docOpenId = params.getValue("openId");
		String answerContent = params.getValue("answerContent");
		//更新问题的医生归属，如果更新成功，则继续回答本问题，否则回答失败
		int updateResult = repository.updateAskForGrabQuestion(askId, docOpenId);
		if(updateResult == 0){
			map.put("result", "-2");
			resp.setData(map);
		}
		
		HttpParts parts = req.getParts();
		byte[] answerImg = parts.get("answerImg");
		byte[] answerVoice = parts.get("answerVoice");
		byte[] answerVedio = parts.get("answerVedio");
		Map<String,Object> docMap = repository.selectDoctorForOpenId(docOpenId);
		
		Answer answer = new Answer();
		answer.setAnswerContent(answerContent);
		if(answerImg != null && answerImg.length > 0){
			String key = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
			if(Strings.isNotEmpty(AliYunOSSUtil.upload(answerImg, key))){
				answer.setAnswerImg(AliYunOSSUtil.LOCALPATH + key);
				logger.error("[CreateAnswer]{}上传图片成功：{}",docOpenId,answer.getAnswerImg());
			}else{
				logger.error("{}上传图片失败",docOpenId);
			}
		}else{
			answer.setAnswerImg(params.getValue("answerImgStr"));
			if(Strings.isEmpty(answer.getAnswerImg())){
				logger.info("[AnswerHandler]{}没有上传图片",docOpenId);
			}
		}
		if(answerVedio != null && answerVedio.length > 0){
			String key = UUID.randomUUID().toString().replaceAll("-", "")+".mp4";
			if(Strings.isNotEmpty(AliYunOSSUtil.upload(answerVedio, key))){
				answer.setAnswerVedio(AliYunOSSUtil.LOCALPATH + key);
				logger.error("[CreateAnswer]{}上传视频成功：{}",docOpenId,answer.getAnswerVedio());
			}else{
				logger.error("{}上传视频失败",docOpenId);
			}
		}else{
			answer.setAnswerVedio(params.getValue("answerVedioStr"));
			if(Strings.isEmpty(answer.getAnswerVedio())){
				logger.info("[AnswerHandler]{}没有上传视频",docOpenId);
			}
		}
		if(answerVoice != null && answerVoice.length > 0){
			String key = UUID.randomUUID().toString().replaceAll("-", "")+".mp3";
			if(Strings.isNotEmpty(AliYunOSSUtil.upload(answerVoice, key))){
				answer.setAnswerVoice(AliYunOSSUtil.LOCALPATH + key);
				logger.error("[CreateAnswer]{}上传语音成功：{}",docOpenId,answer.getAnswerVoice());
			}else{
				logger.error("{}上传语音失败",docOpenId);
			}
		}else{
			answer.setAnswerVoice(params.getValue("answerVoiceStr"));
			if(Strings.isEmpty(answer.getAnswerVoice())){
				logger.info("[AnswerHandler]{}没有上传语音",docOpenId);
			}
			
		}
		
		answer.setAskId(Long.parseLong(askId));
		answer.setDocImg((String)docMap.get("doc_img"));
		answer.setDocName((String)docMap.get("doc_name"));
		answer.setGoodNum(0);
		answer.setGwtCreateTime(Clock.systemUTC().millis());
		answer.setHospitalLogo((String)docMap.get("doc_hospital_img"));
		
		if(repository.saveAnswer(answer)){
			map.put("result", "1");
		}else{
			map.put("result", "0");
		}
		//MOCK
//		resp.setData(repository.queryAnswerByAskId(req.getParams().getValue("answerId")));
		resp.setData(map);
	}
}
