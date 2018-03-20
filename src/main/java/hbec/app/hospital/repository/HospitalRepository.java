package hbec.app.hospital.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.annotations.Repository;
import com.xy.platform.commons.exceptions.DbServiceException;
import com.xy.platform.commons.services.IDbService;
import com.xy.platform.commons.utils.Strings;

import hbec.app.hospital.domain.Answer;
import hbec.app.hospital.domain.AskAndAnswerDomain;
import hbec.app.hospital.domain.DoctorGroup;
import hbec.app.hospital.domain.IndexQuerCondition;
import hbec.app.hospital.domain.QuestionType;

@Repository
public class HospitalRepository {

	@Inject
	private IDbService dbService;
	
	private Logger logger = LoggerFactory.getLogger(HospitalRepository.class);
	
	public int getDoctorPrice(String docId){
		try{
			Map<String,Object> param = new HashMap<>();
			param.put("docId", docId);
			return dbService.select("doctor.getDoctorPrice", param);
		}catch(Exception e){
			logger.error("", e);
		}
		return 30;
	}
	
	public List<AskAndAnswerDomain> index(IndexQuerCondition condition){
		try{
			List<Map<String,Object>> docOpenIdListMap = dbService.list("index.selectFollowDocIds", condition.getOpenId());
			List<Object> docOpenIdList =  new ArrayList<>();
			if(docOpenIdListMap != null && docOpenIdListMap.size() > 0){
				docOpenIdListMap.forEach(value -> {
					if(value != null)
						docOpenIdList.add(value.get("doc_openid"));
				});
			}
			
			List<Map<String,Object>> questionIdListMap = dbService.list("index.selectFollowQuestionIds", condition.getOpenId());
			List<Object>  questionTypeList =  new ArrayList<>();
			if(questionIdListMap != null && questionIdListMap.size() > 0){
				questionIdListMap.forEach(value -> {
					if(value != null)
						questionTypeList.add(value.get("question_id"));
				});
			}
			
			List<AskAndAnswerDomain> indexList = null;
			if(docOpenIdList.size() == 0 && questionTypeList.size() == 0){
				//如果没有查询到任何关注，则直接显示最近10条问答
				logger.debug("[Index] show top 10.");
				indexList = dbService.list("index.selectForFollowTop10", docOpenIdList.toString());
				
			}else{
				logger.debug("[Index] show all for {}.",condition.getOpenId());
				Map<String,List<Object>> paraMap = new HashMap<String,List<Object>>();
				if(docOpenIdList.size() == 0){
					docOpenIdList.add("");
				}
				if(questionTypeList.size() == 0){
					questionTypeList.add("");
				}
				paraMap.put("docOpenId", docOpenIdList);
				paraMap.put("questionTypeId", questionTypeList);
				logger.info("[Index]index params: {}", paraMap);
				indexList = dbService.list("index.selectForFollow", paraMap);
			}
			
			return indexList;
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return Lists.newArrayList();
	}
	
	//
	public List<AskAndAnswerDomain> indexSelf(IndexQuerCondition condition){
		try{
			List<AskAndAnswerDomain> result = new ArrayList<>();
			Map<String,Object> map = dbService.select("index.isDoctor", condition.getOpenId());
			if(null != map && map.size() > 0){
				//current user is doctor.
				result = dbService.list("index.selectQutionForDoc", condition.getOpenId());
				if(result == null || result.size() == 0){
					return indexSelfForGrabQuestion(condition);
				}
			}else{
				//current user is normal user.
				result = dbService.list("index.selectSelfQuestion", condition.getOpenId());
			}
			
			return result;
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return Lists.newArrayList();
	}
	
	private List<AskAndAnswerDomain> indexSelfForGrabQuestion(IndexQuerCondition condition){
		try{
			Map<String,Object> doctorMap = dbService.select("doctor.selectDocFromOpenId", condition.getOpenId());
			if(doctorMap != null && doctorMap.size() > 0){
				String docQuestion = (String)doctorMap.get("doc_question");
				List<String> questionList = new ArrayList<>();
				if(Strings.isNotEmpty(docQuestion)){
					/*StringTokenizer token = new StringTokenizer(docQuestion, ",");
					if(token.hasMoreElements()){
						questionList.add(token.nextToken());
					}*/
					questionList = Arrays.asList(docQuestion.split(","));
				}
				if(questionList.size() > 0){
					logger.info("[indexSelfForGrabQuestion]params: {}, src: {}", questionList, docQuestion);
					Map<String,List<String>> pMap = new HashMap<>();
					pMap.put("questionTypeNames", questionList);
					return dbService.list("index.selectGrabQutionForDoc",pMap);
				}
			}
			
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return Lists.newArrayList();
	}
	
	public List<AskAndAnswerDomain> queryAnswerByAskId(String askId){
		try{
			return dbService.list("answer.selectByAskId", askId);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return Lists.newArrayList();
	} 
	
	public boolean saveAnswer(Answer answer){
		try{
			
			dbService.insert("answer.insert", answer);
			return true;
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return false;
	}
	
	//
	public List<Map<String,Object>> selectDocFromQuestionTypeName(String questionTypeName){
		try{
			return dbService.list("doctor.selectDocFromQuestionTypeName", questionTypeName);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return Lists.newArrayList();
	} 
	
	public List<QuestionType> selectQuestionType(){
		try{
			return dbService.list("index.selectQuestionType", null);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("{}" , e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	public List<DoctorGroup> selectDoctorGroup(){
		try{
			return dbService.list("index.selectDoctorGroup", null);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("{}" , e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	public Map<String,Object> selectDoctorForOpenId(String docOpenId){
		try{
			return dbService.select("doctor.selectDocFromOpenId", docOpenId);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return Maps.newHashMap();
	}
	
	public List<Map<String,Object>> selectDoctorsForGroup(String groupTypeName, String openId){
		try{
			List<Map<String,Object>> result = dbService.list("doctor.doctorsFromGroupTypeName", groupTypeName);
			if(result != null && result.size() > 0){
				List<Map<String,Object>> follows = dbService.list("follow.getAllFollowDoctors", openId);
				result.forEach(map -> {
					for(Map<String,Object> fm : follows){
						if(map.get("docOpenId").equals(fm.get("docOpenId"))){
							map.put("followOrNot", "1");
							break;
						}
					}
					if(!map.containsKey("followOrNot")){
						map.put("followOrNot", "0");
					}
				});
			}
//			return dbService.list("doctor.doctorsFromGroupTypeName", groupTypeName);
			return result;
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("{}" , e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	public List<Map<String,Object>> selectDoctorsForQuestion(String questionTypeName,String openId){
		try{
			List<Map<String,Object>> result = dbService.list("doctor.doctorsFromQuestionTypeName", questionTypeName);
			if(result != null && result.size() > 0){
				List<Map<String,Object>> follows = dbService.list("follow.getAllFollowDoctors", openId);
				result.forEach(map -> {
					for(Map<String,Object> fm : follows){
						if(map.get("docOpenId").equals(fm.get("docOpenId"))){
							map.put("followOrNot", "1");
							break;
						}
					}
					if(!map.containsKey("followOrNot")){
						map.put("followOrNot", "0");
					}
				});
			}
//			return dbService.list("doctor.doctorsFromQuestionTypeName", questionTypeName);
			return result;
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("{}" , e.getMessage());
		}
		return Lists.newArrayList();
	}

	public int followQuestion(String openId, String questionId,
			String questionName) {
		Map<String,Object> map = new HashMap<>();
		map.put("openId", openId);
		map.put("questionId", questionId);
		map.put("questionName", questionName);
		
		try {
			return dbService.insert("follow.followQuestion", map);
		} catch (DbServiceException e) {
			logger.error("", e);
		}
		return 0;
		
	}
	
	public int unfollowQuestion(String openId, String questionId,
			String questionName) {
		Map<String,Object> map = new HashMap<>();
		map.put("openId", openId);
		map.put("questionId", questionId);
		map.put("questionName", questionName);
		
		try {
			return dbService.update("follow.unfollowQuestion", map);
		} catch (DbServiceException e) {
			logger.error("", e);
		}
		return 0;
		
	}
	
	public int followDoctor(String openId, String docOpenId) {
		Map<String,Object> map = new HashMap<>();
		map.put("openId", openId);
		map.put("docOpenId", docOpenId);
		
		try {
			dbService.insert("follow.followDoctor", map);
			return dbService.update("follow.addFollowNum", map);
		} catch (DbServiceException e) {
			logger.error("", e);
		}
		return 0;
		
	}
	
	public int unfollowDoctor(String openId, String docOpenId) {
		Map<String,Object> map = new HashMap<>();
		map.put("openId", openId);
		map.put("docOpenId", docOpenId);
		
		try {
			dbService.update("follow.unfollowDoctor", map);
			return dbService.update("follow.subtractFollowNum", map);
		} catch (DbServiceException e) {
			logger.error("", e);
		}
		return 0;
		
	}
	
	public int updateAskForGrabQuestion(String askId, String docOpenId){
		try{
			Map<String,Object> paraMap = new HashMap<>();
			paraMap.put("askId", askId);
			paraMap.put("docOpenId", docOpenId);
			String openId = dbService.select("ask.selectDocOpenId", askId);
			if(!Strings.isEmpty(openId)){
				if(!docOpenId.equals(openId)){
					logger.error("[answer]askDocOpenId: {}, currentOpenId: {}", openId, docOpenId);
					return 0;
				}
			}
			Map<String,Object> docMap = dbService.select("doctor.selectDocFromOpenId", docOpenId);
			paraMap.put("docId", docMap.get("id"));
			return dbService.update("ask.updateGrab", paraMap);
			
		}catch(Exception e){
			logger.error("", e);
		}
		return 0;
	}
	
}
