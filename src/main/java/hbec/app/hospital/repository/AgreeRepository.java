package hbec.app.hospital.repository;

import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.annotations.Repository;
import hbec.platform.commons.services.IDbService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

@Repository
public class AgreeRepository {

	@Inject
	private IDbService dbService;
	
	private static Logger logger = LoggerFactory.getLogger(AgreeRepository.class);
	
	public int saveAgree(String openId, String docOpenId, String answerId){
		try{
			Map<String,String> param = new HashMap<String,String>();
			param.put("openId", openId);
			param.put("docOpenId", docOpenId);
			param.put("answerId", answerId);
			int result = dbService.insert("agree.saveAgree", param);
			dbService.update("agree.addAnserGoodNum", answerId);
			dbService.update("agree.addDoctorGoodNum", docOpenId);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage()+"\r\n",e);
		}
		return 0;
	}
	
	public int saveComment(String answerId, String openId, String comment, String avatarUrl, String nickName){
		try{
			Map<String,String> param = new HashMap<String,String>();
			param.put("openId", openId);
			param.put("comment", comment);
			param.put("answerId", answerId);
			param.put("avatarUrl", avatarUrl);
			param.put("nickName", nickName);
			return dbService.insert("agree.saveComment", param);
		}catch(Exception e){
			logger.error(e.getMessage()+"\r\n",e);
		}
		return 0;
	}
	
	public List<Map<String,Object>> queryComment(String answerId){
		try{
			return dbService.list("agree.queryComment", answerId);
		}catch(Exception e){
			logger.error(e.getMessage()+"\r\n",e);
		}
		return Lists.newArrayList();
	}
}
