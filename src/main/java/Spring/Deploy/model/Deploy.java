package Spring.Deploy.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Deploy {
    public Deploy(){
        this.Tracking_number = Tracking_number;
        this.Adress_Off_Sender = Adress_Off_Sender;
        this.Date_Of_Depature = Date_Of_Depature;
        this.Adress_Off_Appointment = Adress_Off_Appointment;
        this.Status = Status;
    }

    private String Tracking_number;
    private String Adress_Off_Sender;
    private String Adress_Off_Appointment;
    private String Status;
    private String Date_Of_Depature;

    public Deploy(String trackingNumber, String adressOffSender, String adressOffAppointment, String status, String dateOfDepature) {
    }
}


//localhost:8080/api/v1/deploy/save_deploy_xml

//localhost:8080/api/v1/deploy/save_deploy
            //"Tracking_number":"3123",
            //"Adress_Off_Sender":"gpdjivjs",
            //"Adress_Off_Appointment":"dkjvnjksnwv",
            //"Status":"devilered",
            //"Date_Of_Depature":"01:03:2024"