package hbec.app.hospital.domain;

import java.io.Serializable;

public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long askId;
	private String answerContent;
	private String docName;
	private String docImg;
	private int goodNum;
	private long gwtCreateTime;
	private String answerVoice;
	private String answerVedio;
	private String answerImg;
	private long gwtModifyTime;
	private String hospitalLogo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAskId() {
		return askId;
	}

	public void setAskId(long askId) {
		this.askId = askId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
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

	public int getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}

	public long getGwtCreateTime() {
		return gwtCreateTime;
	}

	public void setGwtCreateTime(long gwtCreateTime) {
		this.gwtCreateTime = gwtCreateTime;
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

	public String getAnswerImg() {
		return answerImg;
	}

	public void setAnswerImg(String answerImg) {
		this.answerImg = answerImg;
	}

	public long getGwtModifyTime() {
		return gwtModifyTime;
	}

	public void setGwtModifyTime(long gwtModifyTime) {
		this.gwtModifyTime = gwtModifyTime;
	}

	public String getHospitalLogo() {
		return hospitalLogo;
	}

	public void setHospitalLogo(String hospitalLogo) {
		this.hospitalLogo = hospitalLogo;
	}

}
