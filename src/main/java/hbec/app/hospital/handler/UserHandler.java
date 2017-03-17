package hbec.app.hospital.handler;

import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;
import hbec.platform.commons.container.ReadOnlyHttpParams;

public class UserHandler {
	
	/**
	 * 关注问题
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris="/user/follow")
	public void follow(IRequest req, IJsonResponse resp){
		ReadOnlyHttpParams params = req.getParams();
		String openId = params.getValue("openId");
		String questionId = params.getValue("questionId");
		String questionName = params.getValue("questionName");
		
	}
}
