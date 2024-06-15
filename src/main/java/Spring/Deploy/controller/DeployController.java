package Spring.Deploy.controller;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.service.DeployService;
import jakarta.ws.rs.QueryParam;
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
    @Autowired
    @GetMapping
    public List<Deploy> findAllDeploy()
    {
        return service.findAllDeploy();
    }
    @GetMapping("/student/info")
    public String getStudentInfo() {
        return "ФИО студента: Валитов Ренат Шамилевич" + "\n" +
                "Группа: УИС-211" + "\n" +
                "Номер варианта: 1.27" + "\n" +
                "Название проекта: Почтовые отправления" + "\n" +
                "Версия Java: 21" + "\n" +
                "Текущий год: 2024";
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
    public Deploy deleteDeploy(@RequestParam("tracking_number") String trackingNumber) {
        return service.deleteDeploy(trackingNumber);
    }

    @GetMapping("assim")
    public double Assimetry()
    {
        return service.assimmetry();
    }
}
