package io.human0722.hutools;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.io.File;
import java.util.HashMap;

/**
 * @author xueliang
 * @description Hutools Xml Util 验证
 * @date 2022-03-10 3:35 PM
 **/
public class XmlUtil {
    public static void main(String[] args){
        File file = new File("/Users/rebot0722/Desktop/puhou/script/lab/OSquery-result.xml");
        Document document = cn.hutool.core.util.XmlUtil.readXML(file);
        NodeList entries = document.getElementsByTagName("entry");
        HashMap<String, Double> info = new HashMap<>();
        for (int i = 1; i < entries.getLength(); i++) {
            Document item = entries.item(i).getOwnerDocument();
            Node item1 = entries.item(i);
            String byXPath = (String)cn.hutool.core.util.XmlUtil.getByXPath("//entry/title", item1, XPathConstants.STRING);
            Double cloud = new Double(cn.hutool.core.util.XmlUtil.getByXPath("//entry/double", item1, XPathConstants.NUMBER).toString());
            System.out.println(byXPath.toString() +  " : " + cloud.toString());
        }
    }
}
