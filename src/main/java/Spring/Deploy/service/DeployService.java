package Spring.Deploy.service;

import Spring.Deploy.model.Deploy;

import java.util.List;


public interface DeployService {
    List<Deploy> findAllDeploy();
    Deploy saveDeploy(Deploy deploy);
    Deploy findByTrackNumb(String tracking_number);
    Deploy updateDeploy(Deploy deploy);


    void deleteDeploy(String tracking_number);

    void saveDeploysToXmlFile();
}
