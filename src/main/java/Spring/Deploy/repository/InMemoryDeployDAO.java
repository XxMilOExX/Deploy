package Spring.Deploy.repository;
import Spring.Deploy.Parse.SaxxParser;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.model.Root;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.IntStream;



@Repository
public class InMemoryDeployDAO {

    SaxxParser parser = new SaxxParser();
    List<Deploy> root = parser.parse();
    private  List<Deploy> DEPLOYS = root;


    public List<Deploy> findAllDeploy() {
        return DEPLOYS;
    }


    public String saveDeploy(Deploy deploy) {
        // Создание контекста для маршаллизации
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Root.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Создание маршаллизатора
        Marshaller marshaller = null;
        try {
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Установка форматирования вывода
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }

        // Создание объекта Root с данными
        DEPLOYS.add(deploy);
        Root root = new Root(List.of(deploy));
        root.setDeploy(DEPLOYS);

        // Запись данных в XML-файл
        try {
            marshaller.marshal(root, new FileOutputStream("DeploysFile.xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "Deploy successfully saved";
    }

    public Deploy findByTrackNumb(String trackingNumber) {

        return DEPLOYS.stream().filter(element -> element.getTracking_number().equals(trackingNumber))
                .findFirst().orElse(null);
    }

    public Deploy deleteDeploy(Deploy deploy) {
        // Создание контекста для маршаллизации
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Root.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Создание маршаллизатора
        Marshaller marshaller = null;
        try {
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Установка форматирования вывода
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }

        var deployIndex = IntStream.range(0, DEPLOYS.size()).filter(index -> DEPLOYS.get(index).getTracking_number().equals(deploy.getTracking_number())).findFirst().orElse(-1);
        if (deployIndex > -1) {
            DEPLOYS.remove(deployIndex);
            Root root = new Root(List.of(deploy));
            // Запись данных в XML-файл
            try {
                marshaller.marshal(root, new FileOutputStream("DeploysFile.xml"));
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return deploy;
        }
        return null;
    }

    public Deploy updateDeploy(Deploy deploy) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Root.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Создание маршаллизатора
        Marshaller marshaller = null;
        try {
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Установка форматирования вывода
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }

        // Создание объекта Root с данными
        var deployIndex = IntStream.range(0, DEPLOYS.size()).filter(index -> DEPLOYS.get(index).getTracking_number().equals(deploy.getTracking_number())).findFirst().orElse(-1);
        if (deployIndex > -1) {
            DEPLOYS.set(deployIndex, deploy);
            Root root = new Root(List.of(deploy));
            root.setDeploy(DEPLOYS);
            // Запись данных в XML-файл
            try {
                marshaller.marshal(root, new FileOutputStream("DeploysFile.xml"));
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return deploy;
        }
        return null;







    }

}




