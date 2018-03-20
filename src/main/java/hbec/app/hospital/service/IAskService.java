package hbec.app.hospital.service;

import com.xy.platform.commons.annotations.Service;

import hbec.app.hospital.domain.Ask;

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
	
	
	int saveOrder(String askId, String orderNo, int payAmt);
	
	int refund(String askId);
	
	int saveRealOrder(String out_trade_no);
}
