package hbec.app.hospital.domain;

import java.io.Serializable;

public class DocAnswer implements Serializable {

	private Long id;
	private String docOpenid;
	private String doctorName;
	private String doctorPortrait;
	private String hospitalLogo;
	private String userPortrait;
	private String createTime;
	private Long createTimestamp;
	private String question;
	private String answer;
	private int goodNum;
	private Long answerId;
	private Long questionId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDocOpenid() {
		return docOpenid;
	}
	public void setDocOpenid(String docOpenid) {
		this.docOpenid = docOpenid;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorPortrait() {
		return doctorPortrait;
	}
	public void setDoctorPortrait(String doctorPortrait) {
		this.doctorPortrait = doctorPortrait;
	}
	public String getHospitalLogo() {
		return hospitalLogo;
	}
	public void setHospitalLogo(String hospitalLogo) {
		this.hospitalLogo = hospitalLogo;
	}
	public String getUserPortrait() {
		return userPortrait;
	}
	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(Long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	
}
