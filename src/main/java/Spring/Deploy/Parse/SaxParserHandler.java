package Spring.Deploy.Parse;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.model.Root;
import Spring.Deploy.repository.InMemoryDeployDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxParserHandler extends DefaultHandler {
    private static final String TAG_DEPLOY_MAIN = "Deploys";
    private static final String TAG_DEPLOY = "Deploy";
    private static final String TAG_TRACKING = "Tracking_number";
    private static final String TAG_SEND = "Address_Off_Sender";
    private static final String TAG_APPOINT = "Address_Off_Appoint";
    private static final String TAG_STATUS = "Status";
    private static final String TAG_DATE = "Date_Of_Delivery";

    public List<Deploy> deploy = new ArrayList<>();
    Root root = new Root(deploy);
    private String currentTagName;
    private boolean IsDeploys = false;
    private boolean IsDeploy = false;

    private String Tracking_number;
    private String Address_Off_Sender;
    private String Address_Off_Appointment;
    private String Status;
    private String Date_Of_Dep;

    public Root getRoot(){
        return root;
    }

    public List<Deploy> getDeploy() {
        return deploy;
    }

    @Override
    public void startDocument() throws SAXException {
        //System.out.println("Start");
    }

    @Override
    public void endDocument() throws SAXException {
        //System.out.println("End");
        root.setDeploy(deploy);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //System.out.println("Start Element" + qName);
        currentTagName = qName;
        if(currentTagName.equals(TAG_DEPLOY_MAIN )){
            IsDeploys = true;
        }
        else if(currentTagName.equals(TAG_DEPLOY)){
            IsDeploy = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.println("End element" + qName);

        if(qName.equals(TAG_DEPLOY_MAIN)){
            IsDeploys = false;
        }else if(qName.equals(TAG_DEPLOY)){
            IsDeploy = false;
            Deploy deploys = new Deploy(Tracking_number,Address_Off_Sender,Address_Off_Appointment,Status,Date_Of_Dep);
            deploy.add(deploys);
        }

        currentTagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //System.out.println("characters " + new String(ch, start, length));
        if(currentTagName == null){
            return;
        }
        if(!IsDeploys && currentTagName.equals(TAG_DEPLOY_MAIN)){
            root.setName(new String(ch, start, length));
        }
        if(IsDeploys && IsDeploy){
            if(currentTagName.equals(TAG_TRACKING)){
                Tracking_number = new String(ch, start, length);
            }
        } if (currentTagName.equals(TAG_APPOINT)){
            Address_Off_Appointment = new String(ch, start, length);

        } if (currentTagName.equals(TAG_SEND)){
            Address_Off_Sender = new String(ch, start, length);

        } if (currentTagName.equals(TAG_STATUS)){
            Status = new String(ch, start, length);

        } if (currentTagName.equals(TAG_DATE)){
            Date_Of_Dep = new String(ch, start, length);

        }
    }


}
