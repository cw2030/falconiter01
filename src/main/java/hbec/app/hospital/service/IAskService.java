package hbec.app.hospital.service;

import hbec.app.hospital.domain.Ask;
import hbec.platform.commons.annotations.Service;

@Service
public interface IAskService {

	/**
	 * 保存用户提问
	 * @param ask
	 * @return
	 */
	int save(Ask ask);
	
	/**
	 * 根据问题ID获取问题信息
	 * @param askId
	 * @return
	 */
	Ask get(long askId);
}
