package hbec.app.hospital.handler;

import com.xy.platform.commons.annotations.Form;
import com.xy.platform.commons.annotations.HbecUriHandler;
import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.container.IJsonResponse;
import com.xy.platform.commons.container.IRequest;

import hbec.app.hospital.domain.IntroducerInfo;
import hbec.app.hospital.repository.IntroduceRepository;

public class IntroduceHandler
{
  @Inject
  private IntroduceRepository repository;
  
  @HbecUriHandler(uris={"/invitation"})
  public void introduce(IRequest req, IJsonResponse resp, @Form IntroducerInfo introducer)
  {
    resp.setData(this.repository.saveIntroduceInfo(introducer));
  }
  
  @HbecUriHandler(uris={"/doctor"})
  public void getDoctorInfo(IRequest req, IJsonResponse resp)
  {
    String openId = req.getParams().getValue("openId");
    resp.setData(this.repository.getDoctorInfo(openId));
  }
}
