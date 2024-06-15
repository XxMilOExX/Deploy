package Spring.Deploy.model;
import com.sun.xml.txw2.annotation.XmlElement;
import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;
// Other necessary imports

@Getter
@XmlRootElement(name = "Deploy")
public class Deploy {

    @Getter
    private String Tracking_number;
    private String Address_Off_Sender;
    private String Address_Off_Appointment;
    private String Status;
    private String Date_Of_Dep;

    public Deploy(String tracking_number, String address_Off_Sender, String address_Off_Appointment, String status, String date_Of_Dep) {
        Tracking_number = tracking_number;
        Address_Off_Sender = address_Off_Sender;
        Address_Off_Appointment = address_Off_Appointment;
        Status = status;
        Date_Of_Dep = date_Of_Dep;
    }
    public Deploy(){}

    public void setTracking_number(String tracking_number) {
        Tracking_number = tracking_number;
    }


    public void setAddress_Off_Sender(String address_Off_Sender) {
        Address_Off_Sender = address_Off_Sender;
    }


    public void setAddress_Off_Appointment(String address_Off_Appointment) {
        Address_Off_Appointment = address_Off_Appointment;
    }


    public void setStatus(String status) {
        Status = status;
    }


    public void setDate_Of_Dep(String date_Of_Dep) {
        Date_Of_Dep = date_Of_Dep;
    }

    @Override
    public String toString() {
        return "Deploys{" +
                ", Tracking_number='" + Tracking_number + '\'' +
                ", Address_Off_Sender='" + Address_Off_Sender + '\'' +
                ", Address_Off_Appointment='" + Address_Off_Appointment + '\'' +
                ", Status='" + Status + '\'' +
                ", Date_Of_Dep='" + Date_Of_Dep + '\'' +
                '}';
    }

}



//localhost:8080/api/v1/deploy/save_deploy_xml

//localhost:8080/api/v1/deploy/save_deploy
            //"Tracking_number":"3123",
            //"Adress_Off_Sender":"gpdjivjs",
            //"Adress_Off_Appointment":"dkjvnjksnwv",
            //"Status":"devilered",
            //"Date_Of_Depature":"01:03:2024"