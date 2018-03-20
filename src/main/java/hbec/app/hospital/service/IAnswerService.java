package hbec.app.hospital.service;

import com.xy.platform.commons.annotations.Service;

import hbec.app.hospital.domain.Answer;


@Service
public interface IAnswerService {

	/**
	 * 根据问题ID查找对应的回答信息
	 * @param askId
	 * @return
	 */
	Answer get(long askId);
}
