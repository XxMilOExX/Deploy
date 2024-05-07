package Spring.Deploy.controller;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.service.DeployService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deploy")
@AllArgsConstructor
public class DeployController {
    @Autowired
    private final DeployService service;
    @GetMapping
    public List<Deploy> findAllDeploy()
    {
        return service.findAllDeploy();
    }
    @PostMapping("save_deploy")
    public String saveDeploy(@RequestBody Deploy deploy)
    {
        service.saveDeploy(deploy);
        return "Deploy successfully saved";
    }
    @GetMapping("save_deploy_xml")
    public void saveDeployXml()
    {
        service.saveDeploysToXmlFile();
    }
    @GetMapping("/{tracking_number}")
    public Deploy findByTrackNumb(@PathVariable String tracking_number){
        return service.findByTrackNumb(tracking_number);
    }
    @PutMapping("update_student")
    public Deploy updateDeploy(@RequestBody Deploy deploy)
    {
        return service.updateDeploy(deploy);
    }
    @DeleteMapping("delete_deploy/{tracking_number}")
    public void deleteDeploy(@PathVariable String tracking_number){
        service.deleteDeploy(tracking_number);
    }
}
