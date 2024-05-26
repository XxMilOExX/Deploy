package Spring.Deploy;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.repository.InMemoryDeployDAO;
import Spring.Deploy.service.DeployService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InMemoryDeployService implements DeployService {
    private final InMemoryDeployDAO repository;

    @Override
    public List<Deploy> findAllDeploy() {
        return repository.findAllDeploy();
    }

    @Override
    public String saveDeploy(Deploy deploy) {
        return repository.saveDeploy(deploy);
    }

    @Override
    public void writeDeploysToXml(List<Deploy> deploy) {

    }


    @Override
    public Deploy findByTrackNumb(String tracking_number) {
        return repository.findByTrackNumb(tracking_number);
    }

    @Override
    public Deploy updateDeploy(Deploy deploy) {
        return repository.updateDeploy(deploy);
    }
    @Override
    public Deploy deleteDeploy(Deploy deploy) {
       return repository.deleteDeploy(deploy);
    }

}
