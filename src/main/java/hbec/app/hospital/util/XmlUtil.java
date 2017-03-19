package hbec.app.hospital.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 转换工具
 * 
 * @author Administrator
 * 
 */
public class XmlUtil {

	public static Map<String, Object> parseXml(String xmlStr) {
		
		Map<String, Object> result = null;
		try {
			// JAXBContext context = JAXBContext.newInstance(HashMap.class);
			// Unmarshaller unmarshaller = context.createUnmarshaller();
			// result = (HashMap) unmarshaller.unmarshal(new
			// StringReader(xmlStr));

			Document doc = DocumentHelper.parseText(xmlStr);
			Element rootElement = doc.getRootElement();
			result = new HashMap<String, Object>();
			ele2map(result, rootElement);
			// 到此xml2map完成，下面的代码是将map转成了json用来观察我们的xml2map转换的是否ok
			String string = JSONObject.fromObject(result).toString();
			// System.out.println("=========" + string);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/***
	 * 核心方法，里面有递归调用
	 * 
	 * @param map
	 * @param ele
	 */
	static void ele2map(Map map, Element ele) {
		// System.out.println(ele);
		// 获得当前节点的子节点
		List<Element> elements = ele.elements();
		if (elements.size() == 0) {
			// 没有子节点说明当前节点是叶子节点，直接取值即可
			map.put(ele.getName(), ele.getText());
		} else if (elements.size() == 1) {
			// 只有一个子节点说明不用考虑list的情况，直接继续递归即可
			Map<String, Object> tempMap = new HashMap<String, Object>();
			ele2map(tempMap, elements.get(0));
			map.put(ele.getName(), tempMap);
		} else {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for (Element element : elements) {
				tempMap.put(element.getName(), null);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = elements.get(0).getNamespace();
				List<Element> elements2 = ele.elements(new QName(string,
						namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Map> list = new ArrayList<Map>();
					for (Element element : elements2) {
						Map<String, Object> tempMap1 = new HashMap<String, Object>();
						ele2map(tempMap1, element);
						list.add(tempMap1);
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					Map<String, Object> tempMap1 = new HashMap<String, Object>();
					ele2map(tempMap1, elements2.get(0));
					map.put(string, tempMap1);
				}
			}
		}
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml)
			throws JDOMException, IOException {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document doc = builder.build(in);
		org.jdom.Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			org.jdom.Element e = (org.jdom.Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XmlUtil.getChildrenText(children);
			}
			m.put(k, v);
		}
		// 关闭流
		in.close();
		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				org.jdom.Element e = (org.jdom.Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XmlUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取xml编码字符集
	 * 
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException,
			IOException {
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document doc = builder.build(in);
		in.close();
		return (String) doc.getProperty("encoding");
	}

	public static void main(String[] args) {
		// String xmlStr =
		// "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[输入xml参数格式错误]]></return_msg><result_code><![CDATA[FAIL]]></result_code><err_code><![CDATA[XML_ERROR]]></err_code><err_code_des><![CDATA[输入xml参数格式错误]]></err_code_des><mch_id>0</mch_id><total_amount>0</total_amount></xml>";
		// Map<String, Object> result = parseXml(xmlStr);
		// String res = ((Map<String, String>) result.get("result_code"))
		// .get("result_code");
		// System.out.println(UUID.randomUUID().toString().toUpperCase());
		// System.out.println(System.currentTimeMillis());
		List<Float> moneyList = new ArrayList<Float>();
		float shareMoney = 0.02f;
		double shareSplitCount = shareMoney / 200.0;
		double count = Math.floor(shareSplitCount);
		if (count >= 1) {
			int ct = Double.valueOf(count).intValue();
			for (int i = 0; i < count; i++) {
				moneyList.add(200.0f);
			}
			float lea = shareMoney - 200.0f * ct;
			if (lea > 0) {
				moneyList.add(lea);
			}
		} else {
			moneyList.add(shareMoney);
		}

		for (Float ft : moneyList) {
			System.out.println(ft);
		}
	}

	public static String toXml(Map<String, Object> data) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        for (String key : data.keySet()) {
            Element keyElement = nodeElement.addElement(key);
            keyElement.setText(String.valueOf(data.get(key)));
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            return out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
