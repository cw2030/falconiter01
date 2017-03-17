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
			List<Map<String,Object>> docIdList = dbService.list("doctor.selectDocFromFollow", condition.getOpenId());
			StringBuilder builder = new StringBuilder();
			docIdList.forEach(value -> {
				builder.append(value).append(",");
			});
			List<AskAndAnswerDomain> indexList = null;
			if(Strings.isEmpty(builder.toString())){
				//如果没有查询到任何关注，则直接显示最近10条问答
				logger.debug("[Index] show top 10.");
				indexList = dbService.list("index.selectForFollowTop10", builder.toString());
				
			}else{
				logger.debug("[Index] show all for {}.",condition.getOpenId());
				indexList = dbService.list("index.selectForFollowDoc", builder.toString());
			}
			
			return indexList;
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
}
