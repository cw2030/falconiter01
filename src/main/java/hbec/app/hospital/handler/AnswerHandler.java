package hbec.app.hospital.handler;

import hbec.app.hospital.domain.Answer;
import hbec.app.hospital.repository.HospitalRepository;
import hbec.app.hospital.util.AliYunOSSUtil;
import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.annotations.RequestMethod;
import hbec.platform.commons.container.HttpParts;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;
import hbec.platform.commons.container.ReadOnlyHttpParams;
import hbec.platform.commons.utils.Strings;

import java.time.Clock;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		/**
		 * private long askId;
	private String answerContent;
	private String docName;
	private String docImg;
	private int goodNum;
	private long gwtCreateTime;
	private String answerVoice;
	private String answerVedio;
	private String answerImg;
	private long gwtModifyTime;
	private String hospitalLogo;
		 */
		ReadOnlyHttpParams params = req.getParams();
		String askId = params.getValue("askId");
		String docOpenId = params.getValue("openId");
		String answerContent = params.getValue("answerContent");
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
			logger.info("[AnswerHandler]{}没有上传图片",docOpenId);
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
			logger.info("[AnswerHandler]{}没有上传视频",docOpenId);
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
			logger.info("[AnswerHandler]{}没有上传语音",docOpenId);
		}
		
		answer.setAskId(Long.parseLong(askId));
		answer.setDocImg((String)docMap.get("doc_img"));
		answer.setDocName((String)docMap.get("doc_name"));
		answer.setGoodNum(0);
		answer.setGwtCreateTime(Clock.systemUTC().millis());
		answer.setHospitalLogo((String)docMap.get("doc_hospital_img"));
		
		
		//MOCK
		resp.setData(repository.queryAnswerByAskId(req.getParams().getValue("answerId")));
	}
}
