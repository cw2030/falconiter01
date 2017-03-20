package hbec.app.hospital.service.impl;

import hbec.app.hospital.domain.Ask;
import hbec.app.hospital.domain.PayRefundResult;
import hbec.app.hospital.service.IAskService;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.exceptions.DbServiceException;
import hbec.platform.commons.services.IDbService;
import hbec.platform.commons.utils.Strings;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


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

	@Override
	public int saveOrder(String askId, String orderNo, int payAmt) {
		try{
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("askId", askId);
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderAmt", payAmt*100);
			logger.info("[Order]pay order : {}", paramMap);
			return dbService.insert("order.insert", paramMap);
		}catch(DbServiceException e){
			logger.error("", e.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		
		return 0;
	}

	@Override
	public int refund(String askId) {
		try{
			Map<String,Object> map = dbService.select("order.selectPayStatus", askId);
			if(map != null && map.size() == 1){
				String refund_order_no = (String)map.get("refund_order_no");
				if(Strings.isEmpty(refund_order_no)){
					
				}else{
					logger.warn("[Refund]askId:{} is already refund:{}",askId,refund_order_no);
					return 0;
				}
				logger.info("[Refund]askId");
			}
			RefundService refund = new RefundService();
			String orderNo = (String)map.get("order_no");
			String refundOrderNo = "R" + System.nanoTime();
			int totalAmt = (Integer)map.get("order_amt");
			PayRefundResult refundResult = refund.refund(orderNo, "", refundOrderNo, Integer.toString(totalAmt)
					, Integer.toString(totalAmt));
			if("SUCCESS".equalsIgnoreCase(refundResult.getReturnCode())){
				logger.info("[Refund]refund success: ",JSON.toJSONString(refundResult));
				return 1;
			}
		}catch(DbServiceException e){
			logger.error("", e.getMessage());
		}catch(Exception e){
			logger.error("", e);
		}
		return 0;
	}

}
