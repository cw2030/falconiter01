package hbec.app.hospital.domain;

import java.io.Serializable;

public class AskAndAnswerDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long questionId;
	private String userName;
	private String userImg;
	private String askTitle;
	private String askContent;
	private String askImg;
	private String askImg2;
	private String askImg3;
	private String askVedio;
	private long answerId;
	private String answerContent;
	private String answerImg;
	private String answerVoice;
	private String answerVedio;
	private String docName;
	private String docImg;
	private String hospitalLogo;
	private long questionTypeId;
	private String questionTypeName;
	private int goodNum;
	private String gwtCreateTime;
	private String gwtModifyTime;
	
	private String openId;
	private String docOpenId;
	private int commentNum;
	
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(long answerId) {
		this.answerId = answerId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getAnswerImg() {
		return answerImg;
	}
	public void setAnswerImg(String answerImg) {
		this.answerImg = answerImg;
	}
	public String getAnswerVoice() {
		return answerVoice;
	}
	public void setAnswerVoice(String answerVoice) {
		this.answerVoice = answerVoice;
	}
	public String getAnswerVedio() {
		return answerVedio;
	}
	public void setAnswerVedio(String answerVedio) {
		this.answerVedio = answerVedio;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocImg() {
		return docImg;
	}
	public void setDocImg(String docImg) {
		this.docImg = docImg;
	}
	public String getHospitalLogo() {
		return hospitalLogo;
	}
	public void setHospitalLogo(String hospitalLogo) {
		this.hospitalLogo = hospitalLogo;
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
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
	public String getGwtCreateTime() {
		return gwtCreateTime;
	}
	public void setGwtCreateTime(String gwtCreateTime) {
		this.gwtCreateTime = gwtCreateTime;
	}
	public String getGwtModifyTime() {
		return gwtModifyTime;
	}
	public void setGwtModifyTime(String gwtModifyTime) {
		this.gwtModifyTime = gwtModifyTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
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
	public int getCommentNum() {
		return commentNum;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	
	
	
}
