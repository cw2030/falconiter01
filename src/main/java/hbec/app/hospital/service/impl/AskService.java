package hbec.app.hospital.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.exceptions.DbServiceException;
import com.xy.platform.commons.services.IDbService;
import com.xy.platform.commons.utils.Strings;

import hbec.app.hospital.domain.Ask;
import hbec.app.hospital.domain.PayRefundResult;
import hbec.app.hospital.service.IAskService;


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

	@Override
	public int saveRealOrder(String out_trade_no) {
		try{
			int askId = dbService.select("ask.getAskId", out_trade_no);
			Map<String,Object> param = Maps.newHashMap();
			param.put("askId",askId);
			return dbService.insert("ask.insertRealOrder", param);
		}catch(Exception e){
			logger.error("", e);
		}
		return 0;
	}

}
