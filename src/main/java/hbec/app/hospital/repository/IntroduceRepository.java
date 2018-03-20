package hbec.app.hospital.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.platform.commons.annotations.Inject;
import com.xy.platform.commons.annotations.Repository;
import com.xy.platform.commons.services.IDbService;

import hbec.app.hospital.domain.IntroducerInfo;

@Repository
public class IntroduceRepository
{
  @Inject
  private IDbService dbService;
  private static Logger logger = LoggerFactory.getLogger(IntroduceRepository.class);
  
  public Map<String, Object> saveIntroduceInfo(IntroducerInfo introducer)
  {
    Map<String, Object> result = new HashMap();
    logger.info("[Introduce]params: {}", introducer);
    try
    {
      int introduceCount = ((Integer)this.dbService.select("introduce.countIntroduceInfo", introducer.getBeIntroducedOpenId())).intValue();
      if (introduceCount > 0)
      {
        result.put("result", "2");
        logger.info("[Introduce]openId: {} is already introduced.", introducer.getBeIntroducedOpenId());
      }
      else
      {
    	if(introducer.getPrice() <= 0){
    		introducer.setPrice(30);
    	}
        this.dbService.insert("introduce.insertIntroduce", introducer);
        this.dbService.insert("introduce.insertDoctor", introducer);
        Map<String, Object> map = (Map)this.dbService.select("introduce.selectDocInfoFromOpenid", introducer.getIntroducerOpenId());
        result.put("result", "1");
        result.put("data", map);
      }
    }
    catch (Exception e)
    {
      logger.error("", e);
      result.put("result", "0");
    }
    return result;
  }
  
  public Map<String, Object> getDoctorInfo(String openId)
  {
    Map<String, Object> result = new HashMap();
    logger.info("[GetDocInfo]openId: {}", openId);
    try
    {
      Map<String, Object> map = (Map)this.dbService.select("introduce.selectDocInfoFromOpenid", openId);
      result.put("data", map);
      result.put("result", "1");
    }
    catch (Exception e)
    {
      logger.error("", e);
      result.put("result", "0");
    }
    return result;
  }
}
