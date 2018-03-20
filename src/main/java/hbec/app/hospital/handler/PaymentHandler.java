package hbec.app.hospital.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.platform.commons.annotations.HbecUriHandler;
import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.container.IJsonResponse;
import com.xy.platform.commons.container.IRequest;
import com.xy.platform.commons.container.ReadOnlyHttpParams;
import com.xy.platform.commons.utils.Strings;

import hbec.app.hospital.service.IAskService;
import hbec.app.hospital.service.impl.PayService;

public class PaymentHandler {
	private static Logger logger = LoggerFactory.getLogger(PaymentHandler.class);
	
	@Inject
	private IAskService askService;
	
	@HbecUriHandler(uris="/pay/pay")
	public void payment(IRequest req, IJsonResponse resp){
		PayService ps = new PayService();
	}

	@HbecUriHandler(uris="/pay/payback")
	public void payback(IRequest req, IJsonResponse resp){
		ReadOnlyHttpParams params = req.getParams();
		String[] names = params.getNames();
		for (String string : names) {
			System.out.println("payback: key:" + string + "--- value: " + params.getValue(string));
		}
		String out_trade_no = params.getValue("out_trade_no");
		if(Strings.isEmpty(out_trade_no)){
			//处理微信回调
			resp.setData("SUCCESS");
			return;
		}
		int result = askService.saveRealOrder(out_trade_no);
		if(result > 0){
			resp.setData("1");
		}else{
			resp.setData("0");
		}
	}
	
	@HbecUriHandler(uris="/pay/refund")
	public void refund(IRequest req, IJsonResponse resp){
		String askId = req.getParams().getValue("askId");
		logger.info("[Refund]askId: {}", askId);
		Map<String,Object> result = new HashMap<>();
		if(Strings.isEmpty(askId)){
			result.put("result", 0);
			return;
		}
		result.put("result", askService.refund(askId));
	}
}
