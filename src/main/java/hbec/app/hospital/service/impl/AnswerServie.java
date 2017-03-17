package hbec.app.hospital.service.impl;

import hbec.app.hospital.domain.Answer;
import hbec.app.hospital.service.IAnswerService;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.exceptions.DbServiceException;
import hbec.platform.commons.services.IDbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnswerServie implements IAnswerService {

	@Inject
	private IDbService dbService;
	
	private static Logger logger = LoggerFactory.getLogger(AnswerServie.class);
	
	@Override
	public Answer get(long askId) {
		try{
			return dbService.select("answer.selectByAskId", askId);
		}catch(DbServiceException de){
			logger.error(de.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return null;
	}

}
