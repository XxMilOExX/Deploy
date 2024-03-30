package Spring.Deploy.repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import Spring.Deploy.model.Deploy;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMamoryDeployDAO {

    private final List<Deploy> DEPLOYS = new ArrayList<>();

    public List<Deploy> findAllDeploy() {
        return DEPLOYS;
    }

    public Deploy saveDeploy(Deploy deploy) {
        DEPLOYS.add(deploy);
        return deploy;
    }

    public Deploy findByTrackNumb(String trackingNumber) {
        return DEPLOYS.stream().filter(element -> element.getTracking_number().equals(trackingNumber))
                .findFirst().orElse(null);
    }

    public void deleteDeploy(String trackingNumber) {
        var deploy = findByTrackNumb(trackingNumber);
        if (deploy != null) {
            DEPLOYS.remove(deploy);
        }
    }

    public Deploy updateDeploy(Deploy deploy) {
        var deployIndex = IntStream.range(0, DEPLOYS.size()).filter(index -> DEPLOYS.get(index).getTracking_number().equals(deploy.getTracking_number())).findFirst().orElse(-1);
        if (deployIndex > -1) {
            DEPLOYS.set(deployIndex, deploy);
            return deploy;
        }
        return null;
    }

    public List<Deploy> loadDeploysFromXmlFile() {
        File file = new File("DeployFile.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            System.out.println("Open parsing error" + e.toString());
            return null;
        }
        Node rootNode = doc.getFirstChild();
        NodeList rootChilds = rootNode.getChildNodes();
        String maindep = null;
        Node DeployNode = null;
        for(int i = 0; i < rootChilds.getLength(); i++){
            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            if (rootChilds.item(i).getNodeName().equals("root")) {
                maindep = rootChilds.item(i).getTextContent();
            }
            if (rootChilds.item(i).getNodeName().equals("Deploys")) {
                DeployNode = rootChilds.item(i);
            }
        }
        if (DeployNode == null){
            return null;
        }
        List<Deploy> deployList = new ArrayList<>();
        NodeList DeployChilds = DeployNode.getChildNodes();
        for(int i = 0; i < DeployChilds.getLength(); i++){
            if (DeployChilds.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            if(DeployChilds.item(i).getNodeName().equals("Deploy")){
                continue;
            }
             String Tracking_number = "";
             String Adress_Off_Sender = "";
             String Adress_Off_Appointment = "";
             String Status = "";
             String Date_Of_Depature = "";
            NodeList deployChilds = DeployChilds.item(i).getChildNodes();
            for (int j = 0; j < deployChilds.getLength(); j ++){
                if (deployChilds.item(i).getNodeType() != Node.ELEMENT_NODE){
                    continue;
                }
                switch (deployChilds.item(j).getNodeName()){
                    case "Tracking_number": {
                        Tracking_number = deployChilds.item(j).getTextContent();

                    }
                    case "Adress_Off_Sender": {
                        Adress_Off_Sender  = deployChilds.item(j).getTextContent();

                    }
                    case "Adress_Off_Appointment": {
                        Adress_Off_Appointment = deployChilds.item(j).getTextContent();

                    }
                    case "Status": {
                        Status = deployChilds.item(j).getTextContent();

                    }
                    case "Date_Of_Depature": {
                        Date_Of_Depature = deployChilds.item(j).getTextContent();

                    }
                }
            }
            Deploy deploy = new Deploy(Tracking_number,Adress_Off_Sender,Adress_Off_Appointment,Status,Date_Of_Depature);
            deployList.add(deploy);
        }
        return deployList;
    }


}


//    public void saveDeploysToXmlFile(List<Deploy> deploys) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.newDocument();
//
//            Element root = doc.createElement("Deploys");
//            doc.appendChild(root);
//
//            for (Deploy deploy : deploys) {
//                Element deployElement = doc.createElement("Deploy");
//                root.appendChild(deployElement);
//
//                Element trackingNumber = doc.createElement("Tracking_number");
//                trackingNumber.appendChild(doc.createTextNode(deploy.getTracking_number()));
//                deployElement.appendChild(trackingNumber);
//
//                Element addressOffSender = doc.createElement("Adress_Off_Sender");
//                addressOffSender.appendChild(doc.createTextNode(deploy.getAdress_Off_Sender()));
//                deployElement.appendChild(addressOffSender);
//
//                Element addressOffAppoint = doc.createElement("Adress_Off_Appoint");
//                addressOffAppoint.appendChild(doc.createTextNode(deploy.getAdress_Off_Appointment()));
//                deployElement.appendChild(addressOffAppoint);
//
//                Element Status = doc.createElement("Status");
//                Status.appendChild(doc.createTextNode(deploy.getStatus()));
//                deployElement.appendChild(Status);
//
//                Element dateOfDepature = doc.createElement("Date_Of_Depature");
//                dateOfDepature.appendChild(doc.createTextNode(deploy.getDate_Of_Depature()));
//                deployElement.appendChild(dateOfDepature);
//
//
//            }
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("DeploysFile.xml"));
//            transformer.transform(source, result);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public List<Deploy> loadDeploysFromXmlFile() {
//        List<Deploy> deploys = new ArrayList<>();
//
//        try {
//            File file = new File("DeploysFile.xml");
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(file);
//
//            Element root = doc.getDocumentElement();
//            NodeList nodeList = root.getElementsByTagName("Deploy");
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//
//                    String tracking_number = element.getElementsByTagName("Tracking_number").item(0).getTextContent();
//                    String address_off_sender = element.getElementsByTagName("Adress_Off_Sender").item(0).getTextContent();
//                    String address_off_appoint = element.getElementsByTagName("Adress_Off_Appoint").item(0).getTextContent();
//                    String status = element.getElementsByTagName("Status").item(0).getTextContent();
//                    String date_off_depature = element.getElementsByTagName("Date_Of_Depature").item(0).getTextContent();
//
//
//                    Deploy deploy = new Deploy(tracking_number, address_off_sender,address_off_appoint,status,date_off_depature);
//                    deploys.add(deploy);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return deploys;
//    }





