package Spring.Deploy.model;

import java.util.List;

public class Root {
    private List<Deploy> deploy;
    private String name;

    public Root(List<Deploy> deploy) {
        this.deploy = deploy;
    }


    public void setDeploy(List<Deploy> deploy) {
        this.deploy = deploy;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Root{" +
                "deploy=" + deploy +
                ", name='" + name + '\'' +
                '}';
    }
}
