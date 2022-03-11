package io.human0722.hutools;

import lombok.Data;
import lombok.ToString;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xueliang
 * @description Hutools Xml Util 验证
 * @date 2022-03-10 3:35 PM
 **/
public class XmlUtil {
    public static void main(String[] args){
        File file = new File("D:/code/java/GeotoolsVerify/src/main/resources/xml/OSquery-result.xml");
        Document document = cn.hutool.core.util.XmlUtil.readXML(file);
        NodeList entries = document.getElementsByTagName("entry");
        ArrayList<SatelliteImageInfo> imageInfos = new ArrayList<>();
        for (int i = 1; i < entries.getLength(); i++) {
            Node item1 = entries.item(20);
            String title = (String)cn.hutool.core.util.XmlUtil.getByXPath("//entry/title", item1, XPathConstants.STRING);
            Double cloudPercentage = new Double(cn.hutool.core.util.XmlUtil.getByXPath("//entry/double", item1, XPathConstants.NUMBER).toString());
            String date = (String)cn.hutool.core.util.XmlUtil.getByXPath("//entry/date[@name='endposition']", item1, XPathConstants.STRING);
            String uuid = (String) cn.hutool.core.util.XmlUtil.getByXPath("//entry/str[@name='uuid']", item1, XPathConstants.STRING);
            String filename = (String) cn.hutool.core.util.XmlUtil.getByXPath("//entry/str[@name='filename']", item1, XPathConstants.STRING);

            SatelliteImageInfo satelliteImageInfo = new SatelliteImageInfo();
            satelliteImageInfo.setTitle(title);
            satelliteImageInfo.setCloudPercentage(cloudPercentage);
            satelliteImageInfo.setDate(date);
            satelliteImageInfo.setUuid(uuid);
            satelliteImageInfo.setFilename(filename);
            imageInfos.add(satelliteImageInfo);
        }
        for(SatelliteImageInfo item : imageInfos) {
            System.out.println(item);
        }

    }

    @Data
    @ToString
    static class SatelliteImageInfo {
        private String title;
        private Double cloudPercentage;
        private String date;
        private String uuid;
        private String filename;

    }
}
