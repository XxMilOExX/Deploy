package Spring.Deploy.Parse;

import Spring.Deploy.model.Deploy;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaxxParser {
    public List<Deploy> parse(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxParserHandler handler = new SaxParserHandler();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
            return null;
        }
        File file = new File("DeploysFile.xml");
        try {
            parser.parse(file, handler);
        } catch (SAXException e) {
            System.out.println("Error sax " + e.toString());;
            return null;
        } catch (IOException e) {
            System.out.println("Error IO " + e.toString());;
            return null;
        }
        return handler.getDeploy();
    }

}