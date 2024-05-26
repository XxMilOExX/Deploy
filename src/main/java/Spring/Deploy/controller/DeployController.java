package Spring.Deploy.controller;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.model.Root;
import Spring.Deploy.service.DeployService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deploy")
@AllArgsConstructor
public class DeployController {
    @Autowired
    private final DeployService service;
    @Autowired
    @GetMapping
    public List<Deploy> findAllDeploy()
    {
        return service.findAllDeploy();
    }
    // Сохранение данных полученных с помощью метода saveDeploy
    @PostMapping("save_deploy")
    public String saveDeploy(@RequestBody Deploy deploy) {
       return service.saveDeploy(deploy);
    }
    @GetMapping("/{tracking_number}")
    public Deploy findByTrackNumb(@PathVariable String tracking_number){
        return service.findByTrackNumb(tracking_number);
    }
    @PutMapping("update_deploy")
    public Deploy updateDeploy(@RequestBody Deploy deploy)
    {
        return service.updateDeploy(deploy);
    }
    @PutMapping("delete_deploy")
    public Deploy deleteDeploy(@RequestBody Deploy deploy){
       return service.deleteDeploy(deploy);
    }
}
