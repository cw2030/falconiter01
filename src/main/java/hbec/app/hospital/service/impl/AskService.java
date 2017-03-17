package hbec.app.hospital.service.impl;

import hbec.app.hospital.domain.Ask;
import hbec.app.hospital.service.IAskService;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.exceptions.DbServiceException;
import hbec.platform.commons.services.IDbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AskService implements IAskService {
	
	@Inject
	private IDbService dbService;
	
	private static Logger logger = LoggerFactory.getLogger("AskService");

	@Override
	public int save(Ask ask) {
		try{
			return dbService.insert("ask.insert", ask);
		}catch(DbServiceException e){
			logger.error("", e.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		
		return 0;
	}

	@Override
	public Ask get(long askId) {
		// TODO Auto-generated method stub
		return null;
	}

}
