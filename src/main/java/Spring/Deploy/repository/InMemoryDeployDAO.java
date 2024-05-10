package Spring.Deploy.repository;
import Spring.Deploy.Parse.SaxxParser;
import Spring.Deploy.model.Root;

import Spring.Deploy.model.Deploy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Repository
public class InMemoryDeployDAO {

    SaxxParser parser = new SaxxParser();
    List<Deploy> root = parser.parse();
    private  List<Deploy> DEPLOYS = root;


    public List<Deploy> findAllDeploy() {
        return DEPLOYS;
    }

    public Deploy saveDeploy(Deploy deploy) {
        try {
            DEPLOYS.add(deploy);
            JAXBContext context = JAXBContext.newInstance(Deploy.class);
            Marshaller marshaller = context.createMarshaller();
            File file = new File("DeploysFile.xml");
            marshaller.marshal(DEPLOYS, file);
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }
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

}




