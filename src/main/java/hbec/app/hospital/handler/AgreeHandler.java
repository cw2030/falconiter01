package hbec.app.hospital.handler;

import hbec.app.hospital.repository.AgreeRepository;
import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;
import hbec.platform.commons.container.ReadOnlyHttpParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class AgreeHandler {
	
	@Inject
	private AgreeRepository repository;
	
	private static Logger logger = LoggerFactory.getLogger(AgreeHandler.class);

	@HbecUriHandler(uris="/agree")
	public void agree(IRequest req, IJsonResponse resp){
		try{
			ReadOnlyHttpParams params = req.getParams();
			String openId = params.getValue("openId");
			String docOpenId = params.getValue("docOpenId");
			String answerId = params.getValue("answerId");
			Map<String,Object> map = new HashMap<>();
			if(Strings.isNullOrEmpty(openId)
					|| Strings.isNullOrEmpty(docOpenId)
					|| Strings.isNullOrEmpty(answerId)){
				logger.info("openId:{},answerId:{},docOpenId:{}",openId,answerId,docOpenId);
				map.put("result", 0);
			}else{
				int resultInt = repository.saveAgree(openId, docOpenId, answerId);
				map.put("result", resultInt);
			}
			
			resp.setData(map);
		}catch(Exception e){
			logger.error("", e);
		}
		
	}
	
	@HbecUriHandler(uris="/comment")
	public void comment(IRequest req, IJsonResponse resp){
		try{
			ReadOnlyHttpParams params = req.getParams();
			String openId = params.getValue("openId");
			String comment = params.getValue("comment");
			String answerId = params.getValue("answerId");
			String avatarUrl = params.getValue("avatarUrl");
			String nickName = params.getValue("nickName");
			Map<String,Object> map = new HashMap<>();
			if(Strings.isNullOrEmpty(openId)
					|| Strings.isNullOrEmpty(comment)
					|| Strings.isNullOrEmpty(answerId)
					|| Strings.isNullOrEmpty(avatarUrl)
					|| Strings.isNullOrEmpty(nickName)){
				logger.info("openId:{},answerId:{},avatarUrl:{},nickName:{},comment:{}",openId,answerId,avatarUrl,nickName,comment);
				map.put("result", 0);
			}else{
				int resultInt = repository.saveComment(answerId, openId, comment, avatarUrl, nickName);
				map.put("result", resultInt);
			}
			
			resp.setData(map);
		}catch(Exception e){
			logger.error("", e);
		}
		
	}
	
	@HbecUriHandler(uris="/comment/{answerId}")
	public void queryComment(IRequest req, IJsonResponse resp){
		try{
			String answerId = req.getParams().getValue("answerId");
			if(Strings.isNullOrEmpty(answerId)){
				logger.info("[QueryComment]answerId is null.");
				resp.setData(new ArrayList<Map<String,Object>>());
				return;
			}
			resp.setData(repository.queryComment(answerId));
			return;
		}catch(Exception e){
			logger.error("", e);
		}
		resp.setData(new ArrayList<Map<String,Object>>());
	}
}
