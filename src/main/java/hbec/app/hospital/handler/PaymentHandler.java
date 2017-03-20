package hbec.app.hospital.handler;

import hbec.app.hospital.service.IAskService;
import hbec.app.hospital.service.impl.PayService;
import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.annotations.Inject;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;
import hbec.platform.commons.container.ReadOnlyHttpParams;
import hbec.platform.commons.utils.Strings;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		resp.setData("ok");
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
