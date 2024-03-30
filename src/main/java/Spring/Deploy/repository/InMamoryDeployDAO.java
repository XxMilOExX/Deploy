package Spring.Deploy.repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import Spring.Deploy.model.Deploy;
import org.springframework.stereotype.Repository;

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
        File file = new File("DeploysFile.xml");
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
        for (int i = 0; i < rootChilds.getLength(); i++) {
            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (rootChilds.item(i).getNodeName().equals("root")) {
                maindep = rootChilds.item(i).getTextContent();
            }
            if (rootChilds.item(i).getNodeName().equals("Deploys")) {
                DeployNode = rootChilds.item(i);
            }
        }
        if (DeployNode == null) {
            return null;
        }
        List<Deploy> deployList = new ArrayList<>();
        NodeList DeployChilds = DeployNode.getChildNodes();
        for (int i = 0; i < DeployChilds.getLength(); i++) {
            if (DeployChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (DeployChilds.item(i).getNodeName().equals("Deploy")) {
                continue;
            }
            String Tracking_number = "";
            String Adress_Off_Sender = "";
            String Adress_Off_Appointment = "";
            String Status = "";
            String Date_Of_Depature = "";
            NodeList deployChilds = DeployChilds.item(i).getChildNodes();
            for (int j = 0; j < deployChilds.getLength(); j++) {
                if (deployChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                switch (deployChilds.item(j).getNodeName()) {
                    case "Tracking_number": {
                        Tracking_number = deployChilds.item(j).getTextContent();

                    }
                    case "Adress_Off_Sender": {
                        Adress_Off_Sender = deployChilds.item(j).getTextContent();

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
            Deploy deploy = new Deploy(Tracking_number, Adress_Off_Sender, Adress_Off_Appointment, Status, Date_Of_Depature);
            deployList.add(deploy);
        }
        return deployList;
    }

    public void saveDeploysToXmlFile() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Deploys");
            doc.appendChild(root);

            for (Deploy deploy : DEPLOYS) {
                Element deployElement = doc.createElement("Deploy");
                root.appendChild(deployElement);

                Element trackingNumber = doc.createElement("Tracking_number");
                trackingNumber.appendChild(doc.createTextNode(deploy.getTracking_number()));
                deployElement.appendChild(trackingNumber);

                Element addressOffSender = doc.createElement("Adress_Off_Sender");
                addressOffSender.appendChild(doc.createTextNode(deploy.getAdress_Off_Sender()));
                deployElement.appendChild(addressOffSender);

                Element addressOffAppoint = doc.createElement("Adress_Off_Appoint");
                addressOffAppoint.appendChild(doc.createTextNode(deploy.getAdress_Off_Appointment()));
                deployElement.appendChild(addressOffAppoint);

                Element Status = doc.createElement("Status");
                Status.appendChild(doc.createTextNode(deploy.getStatus()));
                deployElement.appendChild(Status);

                Element dateOfDepature = doc.createElement("Date_Of_Depature");
                dateOfDepature.appendChild(doc.createTextNode(deploy.getDate_Of_Depature()));
                deployElement.appendChild(dateOfDepature);


            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File file = new File("DeploysFile.xml");
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




