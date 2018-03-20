package hbec.app.hospital.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.platform.commons.annotations.HbecUriHandler;
import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.container.IJsonResponse;
import com.xy.platform.commons.container.IRequest;
import com.xy.platform.commons.container.ReadOnlyHttpParams;

import hbec.app.hospital.repository.HospitalRepository;

public class UserHandler {
	
	@Inject
	private HospitalRepository repository;
	
	private static Logger logger = LoggerFactory.getLogger(UserHandler.class);
	/**
	 * 关注问题
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris="/follow/question")
	public void follow(IRequest req, IJsonResponse resp){
		Map<String,String> map = new HashMap<>();
		try{
			ReadOnlyHttpParams params = req.getParams();
			String openId = params.getValue("openId");
			String questionId = params.getValue("questionId");
			String questionName = params.getValue("questionName");
			logger.info("openId:{},questionId:{},questionName:{}",openId, questionId,questionName);
			repository.followQuestion(openId, questionId,questionName);
			map.put("result", "1");
		}catch(Exception e){
			logger.error("", e);
			map.put("result", "0");
		}
		resp.setData(map);
	}
	@HbecUriHandler(uris="/follow/doctor")
	public void followDoctor(IRequest req, IJsonResponse resp){
		Map<String,String> map = new HashMap<>();
		try{
			ReadOnlyHttpParams params = req.getParams();
			String openId = params.getValue("openId");
			String docOpenId = params.getValue("docOpenId");
			logger.info("openId:{},docOpenId:{},questionName:{}",openId, docOpenId);
			repository.followDoctor(openId, docOpenId);
			map.put("result", "1");
		}catch(Exception e){
			logger.error("", logger);
			map.put("result", "0");
		}
		resp.setData(map);
	}
	
	@HbecUriHandler(uris="/follow/cancel/question")
	public void unfollow(IRequest req, IJsonResponse resp){
		Map<String,String> map = new HashMap<>();
		try{
			ReadOnlyHttpParams params = req.getParams();
			String openId = params.getValue("openId");
			String questionId = params.getValue("questionId");
			String questionName = params.getValue("questionName");
			logger.info("openId:{},questionId:{},questionName:{}",openId, questionId,questionName);
			repository.unfollowQuestion(openId, questionId,questionName);
			map.put("result", "1");
		}catch(Exception e){
			logger.error("", e);
			map.put("result", "0");
		}
		resp.setData(map);
	}
	@HbecUriHandler(uris="/follow/cancel/doctor")
	public void unfollowDoctor(IRequest req, IJsonResponse resp){
		Map<String,String> map = new HashMap<>();
		try{
			ReadOnlyHttpParams params = req.getParams();
			String openId = params.getValue("openId");
			String docOpenId = params.getValue("docOpenId");
			logger.info("openId:{},docOpenId:{},questionName:{}",openId, docOpenId);
			repository.unfollowDoctor(openId, docOpenId);
			map.put("result", "1");
		}catch(Exception e){
			logger.error("", logger);
			map.put("result", "0");
		}
		resp.setData(map);
	}
}
