package Spring.Deploy.service;

import Spring.Deploy.Parse.SaxxParser;
import Spring.Deploy.model.Root;

public class TestaApp {
    public static void main(String[] args) {
        SaxxParser parser = new SaxxParser();
        Root root = parser.parse();
        System.out.println("Root "  + root.toString());
    }
}
