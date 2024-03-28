package Spring.Deploy.repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

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
    public List<Deploy> findAllDeploy()
    {
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
        if (deploy != null){
            DEPLOYS.remove(deploy);
        }
    }

    public Deploy updateDeploy(Deploy deploy) {
        var deployIndex = IntStream.range(0, DEPLOYS.size()).filter(index -> DEPLOYS.get(index).getTracking_number().equals(deploy.getTracking_number())).findFirst().orElse(-1);
        if (deployIndex > -1){
            DEPLOYS.set(deployIndex, deploy);
            return deploy;
        }
        return null;
    }

    public void saveDeploysToXmlFile(List<Deploy> deploys) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Deploys");
            doc.appendChild(root);

            for (Deploy deploy : deploys) {
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
            StreamResult result = new StreamResult(new File("DeploysFile.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Deploy> loadDeploysFromXmlFile() {
        List<Deploy> deploys = new ArrayList<>();

        try {
            File file = new File("DeploysFile.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            Element root = doc.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("Deploy");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String tracking_number = element.getElementsByTagName("Tracking_number").item(0).getTextContent();
                    String address_off_sender = element.getElementsByTagName("Adress_Off_Sender").item(0).getTextContent();
                    String address_off_appoint = element.getElementsByTagName("Adress_Off_Appoint").item(0).getTextContent();
                    String status = element.getElementsByTagName("Status").item(0).getTextContent();
                    String date_off_depature = element.getElementsByTagName("Date_Of_Depature").item(0).getTextContent();


                    Deploy deploy = new Deploy(tracking_number, address_off_sender,address_off_appoint,status,date_off_depature);
                    deploys.add(deploy);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return deploys;
    }

}


