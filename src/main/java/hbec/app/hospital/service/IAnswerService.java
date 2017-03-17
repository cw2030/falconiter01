package hbec.app.hospital.service;

import hbec.app.hospital.domain.Answer;
import hbec.platform.commons.annotations.Service;


@Service
public interface IAnswerService {

	/**
	 * 根据问题ID查找对应的回答信息
	 * @param askId
	 * @return
	 */
	Answer get(long askId);
}
