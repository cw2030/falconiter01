package hbec.app.hospital.service.impl;

import hbec.app.hospital.domain.Constants;
import hbec.app.hospital.domain.PayOrderField;
import hbec.app.hospital.domain.PayRefundParam;
import hbec.app.hospital.domain.PayRefundResult;
import hbec.app.hospital.util.BeanUtil;
import hbec.app.hospital.util.Sha1Util;
import hbec.app.hospital.util.SignUtil;
import hbec.app.hospital.util.WeixinUtil;
import hbec.app.hospital.util.XmlUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefundService {

	// pay 微信支付商户号
    String mchId = Constants.MCHID;

    //mp.weixin  微信公共账号 appId
    String appId = Constants.APPID;

    // 申请开通微信支付后，发给开发者。用于计算签名
    String apiKey = Constants.KEY;

    String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    String refundqueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";
    
    private static Logger logger = LoggerFactory.getLogger(RefundService.class);
    
    public PayRefundResult refund(String orderNo, String wxOrderNo, String refundOrderNo
    		, String totalAmt, String refundAmt) throws Exception{
    	PayRefundParam param = new PayRefundParam();
        // 基本信息
        param.setAppid(appId);
        param.setMchId(mchId);

        param.setOutTradeNo(orderNo); // 客户订单号
        param.setTransactionId(wxOrderNo); //微信订单号


        // 业务信息
        param.setOutRefundNo(refundOrderNo);
        param.setTotalFee(new BigDecimal(totalAmt).multiply(new BigDecimal(100)).intValue()); // 总金额
        param.setRefundFee(new BigDecimal(refundAmt).multiply(new BigDecimal(100)).intValue()); // 退款总金额
        param.setOpUserId(mchId);

        //签名
        param.setNonceStr(Sha1Util.getNonceStr());
        Map<String, Object> data = BeanUtil.object2Map(param); // 参数列表
        param.setSign(SignUtil.sign(data, apiKey)); // 计算sign
        data.put(PayOrderField.SIGN.getField(), param.getSign()); // sign放到map中，为后续转xml
        // 转成xml格式
        String xml = XmlUtil.toXml(data);
        logger.info("[refund]post.xml=" + xml);
        
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("apiclient_cert.p12");
        String resultStr = WeixinUtil.postXmlWithKey(refundUrl, xml, in, mchId);
        logger.info("[refund]result=" + resultStr);

     // 校验返回结果 签名
        Map<String, Object> resultMap = XmlUtil.parseXml(resultStr);
        String resultSign = SignUtil.sign(resultMap, apiKey);
        if (resultMap.get("sign") == null || !resultMap.get("sign").equals(resultSign)) {
            logger.info("[refund]sign is not correct, " + resultMap.get("sign") + " " + resultSign);
            throw new RuntimeException("签名校验不通过");
        }

        PayRefundResult result = BeanUtil.map2Object(PayRefundResult.class, resultMap);
        return result;
    }
}
