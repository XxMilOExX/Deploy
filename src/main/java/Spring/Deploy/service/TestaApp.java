package Spring.Deploy.service;

import Spring.Deploy.Parse.SaxxParser;
import Spring.Deploy.model.Deploy;
import Spring.Deploy.model.Root;

import java.util.List;

public class TestaApp {
    public static void main(String[] args) {
        SaxxParser parser = new SaxxParser();
        List<Deploy> root = parser.parse();
        System.out.println("Root "  + root.toString());
    }
}
