package hbec.app.hospital.handler;

import hbec.app.hospital.service.impl.PayService;
import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;
import hbec.platform.commons.container.ReadOnlyHttpParams;

public class PaymentHandler {
	
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
	public void refund(){
		
	}
}
