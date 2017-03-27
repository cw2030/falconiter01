package hbec.app.hospital.repository;

import hbec.app.hospital.domain.Answer;
import hbec.app.hospital.domain.AskAndAnswerDomain;
import hbec.app.hospital.domain.DoctorGroup;
import hbec.app.hospital.domain.IndexQuerCondition;
import hbec.app.hospital.domain.QuestionType;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.annotations.Repository;
import hbec.platform.commons.exceptions.DbServiceException;
import hbec.platform.commons.services.IDbService;
import hbec.platform.commons.utils.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Repository
public class HospitalRepository {

	@Inject
	private IDbService dbService;
	
	private Logger logger = LoggerFactory.getLogger(HospitalRepository.class);
	
	public List<AskAndAnswerDomain> index(IndexQuerCondition condition){
		try{
			List<Map<String,Object>> docIdList = dbService.list("index.selectFollowDocIds", condition.getOpenId());
			StringBuilder docBuilder = new StringBuilder();
			docIdList.forEach(value -> {
				docBuilder.append(value.get("doc_id")).append(",");
			});
			if(docBuilder.length() > 0){
				docBuilder.setLength(docBuilder.length() - 1);
			}
			
			List<Map<String,Object>> questionIdList = dbService.list("index.selectFollowDocIds", condition.getOpenId());
			StringBuilder questionBuilder = new StringBuilder();
			questionIdList.forEach(value -> {
				questionBuilder.append(value.get("question_id")).append(",");
			});
			if(questionBuilder.length() > 0){
				questionBuilder.setLength(questionBuilder.length() - 1);
			}
			
			List<AskAndAnswerDomain> indexList = null;
			if(Strings.isEmpty(docBuilder.toString()) && Strings.isEmpty(questionBuilder.toString())){
				//如果没有查询到任何关注，则直接显示最近10条问答
				logger.debug("[Index] show top 10.");
				indexList = dbService.list("index.selectForFollowTop10", docBuilder.toString());
				
			}else{
				logger.debug("[Index] show all for {}.",condition.getOpenId());
				Map<String,String> paraMap = new HashMap<String,String>();
				paraMap.put("docId", docBuilder.toString());
				paraMap.put("questionTypeId", docBuilder.toString());
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
			Map<String,Object> map = dbService.select("index.isDoctor", condition.getOpenId());
			if(null != map && map.size() > 0){
				//current user is doctor.
				return dbService.list("index.selectQutionForDoc", condition.getOpenId());
			}else{
				//current user is normal user.
				return dbService.list("index.selectSelfQuestion", condition.getOpenId());
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
	
	public List<Map<String,Object>> selectDoctorsForGroup(String groupTypeName){
		try{
			return dbService.list("doctor.doctorsFromGroupTypeName", groupTypeName);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("{}" , e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	public List<Map<String,Object>> selectDoctorsForQuestion(String questionTypeName){
		try{
			return dbService.list("doctor.doctorsFromQuestionTypeName", questionTypeName);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("{}" , e.getMessage());
		}
		return Lists.newArrayList();
	}
	
}
