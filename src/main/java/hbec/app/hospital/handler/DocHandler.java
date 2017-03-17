package hbec.app.hospital.handler;

import hbec.platform.commons.annotations.HbecUriHandler;
import hbec.platform.commons.container.IJsonResponse;
import hbec.platform.commons.container.IRequest;

public class DocHandler {

	/**
	 * 显示所有常见的问题
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris="/index/question")
	public void questionList(IRequest req, IJsonResponse resp){
		
	}
	
	/**
	 * 显示所有的医生分组
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris="/index/group")
	public void docGroup(IRequest req, IJsonResponse resp){
		
	}
	
	/**
	 * 要所常见问题查询对应的医生列表
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris="/index/q/d")
	public void queryDocForQuestiion(IRequest req, IJsonResponse resp){
		
	}
	
	/**
	 * 要所分组信息查询对应的医生
	 * @param req
	 * @param resp
	 */
	@HbecUriHandler(uris="/index/g/d")
	public void queryDocForGroup(IRequest req, IJsonResponse resp){
		
	}
}
