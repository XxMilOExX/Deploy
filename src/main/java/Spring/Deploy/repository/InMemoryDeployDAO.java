package Spring.Deploy.repository;
import Spring.Deploy.Parse.SaxxParser;

import Spring.Deploy.model.Deploy;
import Spring.Deploy.model.Root;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
        var c = DEPLOYS.stream().filter(element -> element.getTracking_number().equals(trackingNumber))
                .findFirst().orElse(null);
        if(c == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Отправление с ID  не найден");
        }
        else{return c;}
    }

    public Deploy deleteDeploy(String trackingNumber) {
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

        var deployIndex = DEPLOYS.stream().filter(element -> element.getTracking_number().equals(trackingNumber))
                .findFirst().orElse(null);
        if (deployIndex != null) {
            DEPLOYS.remove(deployIndex);
            Root root = new Root(List.of());
            root.setDeploy(DEPLOYS);
            // Запись данных в XML-файл
            try {
                marshaller.marshal(root, new FileOutputStream("DeploysFile.xml"));
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return deployIndex;
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
        if (deployIndex == -1) {}
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
    public double How1InTr() {
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
        List<String> trackingNumbers = new ArrayList<>();
        for (int i = 0; i < DEPLOYS.size(); i++) {
            trackingNumbers.add(DEPLOYS.get(i).getTracking_number());
        }
        List<Integer> howone = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < trackingNumbers.size(); i++) {
            for (int k = 0; k < trackingNumbers.get(i).length(); k++) {
                if (Integer.parseInt(String.valueOf(trackingNumbers.get(i).charAt(k))) == 1) {
                    count++;
                }
            }
            howone.add(count);
            count = 0;
        }
        double xv = 0;
        for (int i = 0; i < howone.size(); i++) {
            xv += howone.get(i);
        }
        xv /= howone.size();
        double m3 = 0;
        for (int i = 0; i < howone.size(); i++) {
            m3 += (double) Math.pow((howone.get(i) - xv), 3);
        }
        m3 /= howone.size();
        double b = 0;
        for (int i = 0; i < howone.size(); i++) {
            b += (double) Math.pow((howone.get(i) - xv), 2);
        }
        b = (double) Math.pow(b / howone.size(), 0.5);
        double A3 = m3 / Math.pow(b, 3);
        return A3;
    }

}




