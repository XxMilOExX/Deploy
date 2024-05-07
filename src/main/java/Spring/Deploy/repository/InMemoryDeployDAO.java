package Spring.Deploy.repository;
import Spring.Deploy.Parse.SaxxParser;
import Spring.Deploy.model.Root;

import Spring.Deploy.model.Deploy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryDeployDAO {
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

    public String loadDeploysFromXmlFile() {
        SaxxParser parser = new SaxxParser();
        Root root = parser.parse();
        return root.toString();
    }

    public void saveDeploysToXmlFile() {
    }
}




