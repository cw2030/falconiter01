package hbec.app.hospital.domain;

import java.io.Serializable;

public class Ask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String openId;
	private String userName;
	private String userImg;
	private String askTitle;
	private String askContent;
	private String askImg;
	private String askImg2;
	private String askImg3;
	private String askVedio;
	private Long gwtCreateTime;
	private Long gwtModifyTime;
	private long questionTypeId;
	private String questionTypeName;
	private long docId;
	private String docOpenId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getAskTitle() {
		return askTitle;
	}
	public void setAskTitle(String askTitle) {
		this.askTitle = askTitle;
	}
	public String getAskContent() {
		return askContent;
	}
	public void setAskContent(String askContent) {
		this.askContent = askContent;
	}
	public String getAskImg() {
		return askImg;
	}
	public void setAskImg(String askImg) {
		this.askImg = askImg;
	}
	public String getAskVedio() {
		return askVedio;
	}
	public void setAskVedio(String askVedio) {
		this.askVedio = askVedio;
	}
	public Long getGwtCreateTime() {
		return gwtCreateTime;
	}
	public void setGwtCreateTime(Long gwtCreateTime) {
		this.gwtCreateTime = gwtCreateTime;
	}
	public Long getGwtModifyTime() {
		return gwtModifyTime;
	}
	public void setGwtModifyTime(Long gwtModifyTime) {
		this.gwtModifyTime = gwtModifyTime;
	}
	public long getQuestionTypeId() {
		return questionTypeId;
	}
	public void setQuestionTypeId(long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}
	public String getQuestionTypeName() {
		return questionTypeName;
	}
	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public String getDocOpenId() {
		return docOpenId;
	}
	public void setDocOpenId(String docOpenId) {
		this.docOpenId = docOpenId;
	}
    public String getAskImg2() {
        return askImg2;
    }
    public void setAskImg2(String askImg2) {
        this.askImg2 = askImg2;
    }
    public String getAskImg3() {
        return askImg3;
    }
    public void setAskImg3(String askImg3) {
        this.askImg3 = askImg3;
    }
	
}
