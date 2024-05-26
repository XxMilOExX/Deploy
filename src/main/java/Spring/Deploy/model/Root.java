package Spring.Deploy.model;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Deploys")
public class Root {
    @lombok.Setter
    private List<Deploy> deploy;
    @lombok.Setter
    private String name;

    public Root() {
    }

    public void setDeploy(List<Deploy> deploy) {
        this.deploy = deploy;
    }

    public Root(List<Deploy> deploy) {
        this.deploy = deploy;
    }
    @Override
    public String toString() {
        return "Root{" +
                "deploy=" + deploy +
                ", name='" + name + '\'' +
                '}';
    }
}
