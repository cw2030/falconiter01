package hbec.app.hospital.domain;

import java.io.Serializable;

public class IntroducerInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String introducerOpenId;
  private String beIntroducedOpenId;
  private String docOpenId;
  private String docName;
  private String docImg;
  private String docEmail;
  private String docPhone;
  private String docTitle;
  private String docGroup;
  private String docQuestion;
  private String imgStr1;
  private String imgStr2;
  private String docDesc;
  private String docHospitalName;
  private String docHospitalImg;
  private String docHospitalAddr;
  private String sex;
  private String docLocation;
  private int price;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getIntroducerOpenId()
  {
    return this.introducerOpenId;
  }
  
  public void setIntroducerOpenId(String introducerOpenId)
  {
    this.introducerOpenId = introducerOpenId;
  }
  
  public String getBeIntroducedOpenId()
  {
    return this.beIntroducedOpenId;
  }
  
  public void setBeIntroducedOpenId(String beIntroducedOpenId)
  {
    this.beIntroducedOpenId = beIntroducedOpenId;
  }
  
  public String getDocOpenId()
  {
    return this.docOpenId;
  }
  
  public void setDocOpenId(String docOpenId)
  {
    this.docOpenId = docOpenId;
  }
  
  public String getDocName()
  {
    return this.docName;
  }
  
  public void setDocName(String docName)
  {
    this.docName = docName;
  }
  
  public String getDocImg()
  {
    return this.docImg;
  }
  
  public void setDocImg(String docImg)
  {
    this.docImg = docImg;
  }
  
  public String getDocPhone()
  {
    return this.docPhone;
  }
  
  public void setDocPhone(String docPhone)
  {
    this.docPhone = docPhone;
  }
  
  public String getDocTitle()
  {
    return this.docTitle;
  }
  
  public void setDocTitle(String docTitle)
  {
    this.docTitle = docTitle;
  }
  
  public String getDocGroup()
  {
    return this.docGroup;
  }
  
  public String getDocEmail()
  {
    return this.docEmail;
  }
  
  public void setDocEmail(String docEmail)
  {
    this.docEmail = docEmail;
  }
  
  public void setDocGroup(String docGroup)
  {
    this.docGroup = docGroup;
  }
  
  public String getDocQuestion()
  {
    return this.docQuestion;
  }
  
  public void setDocQuestion(String docQuestion)
  {
    this.docQuestion = docQuestion;
  }
  
  public String getImgStr1()
  {
    return this.imgStr1;
  }
  
  public void setImgStr1(String imgStr1)
  {
    this.imgStr1 = imgStr1;
  }
  
  public String getImgStr2()
  {
    return this.imgStr2;
  }
  
  public void setImgStr2(String imgStr2)
  {
    this.imgStr2 = imgStr2;
  }
  
  public String getDocDesc()
  {
    return this.docDesc;
  }
  
  public void setDocDesc(String docDesc)
  {
    this.docDesc = docDesc;
  }
  
  public String getDocHospitalName()
  {
    return this.docHospitalName;
  }
  
  public void setDocHospitalName(String docHospitalName)
  {
    this.docHospitalName = docHospitalName;
  }
  
  public String getDocHospitalImg()
  {
    return this.docHospitalImg;
  }
  
  public void setDocHospitalImg(String docHospitalImg)
  {
    this.docHospitalImg = docHospitalImg;
  }
  
  public String getDocHospitalAddr()
  {
    return this.docHospitalAddr;
  }
  
  public void setDocHospitalAddr(String docHospitalAddr)
  {
    this.docHospitalAddr = docHospitalAddr;
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public void setSex(String sex)
  {
    this.sex = sex;
  }
  
  public String getDocLocation()
  {
    return this.docLocation;
  }
  
  public void setDocLocation(String docLocation)
  {
    this.docLocation = docLocation;
  }
  
  public int getPrice() {
	return price;
}

public void setPrice(int price) {
	this.price = price;
}

@Override
public String toString() {
	return "IntroducerInfo [id=" + id + ", introducerOpenId="
			+ introducerOpenId + ", beIntroducedOpenId=" + beIntroducedOpenId
			+ ", docOpenId=" + docOpenId + ", docName=" + docName + ", docImg="
			+ docImg + ", docEmail=" + docEmail + ", docPhone=" + docPhone
			+ ", docTitle=" + docTitle + ", docGroup=" + docGroup
			+ ", docQuestion=" + docQuestion + ", imgStr1=" + imgStr1
			+ ", imgStr2=" + imgStr2 + ", docDesc=" + docDesc
			+ ", docHospitalName=" + docHospitalName + ", docHospitalImg="
			+ docHospitalImg + ", docHospitalAddr=" + docHospitalAddr
			+ ", sex=" + sex + ", docLocation=" + docLocation + ", price="
			+ price + "]";
}
}
