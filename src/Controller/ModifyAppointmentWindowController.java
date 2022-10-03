package Controller;

import DatabaseAccess.ContactQuery;
import DatabaseAccess.CustomerQuery;
import Helper.Utils;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentWindowController implements Initializable {
    public static Appointment appointment;
    @FXML
    private Button backButton;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> custComboBox;

    @FXML
    private TextField custIDTextField;

    @FXML
    private TextField descTextField;

    @FXML
    private ComboBox<Integer> endHourComboBox;

    @FXML
    private ComboBox<Integer> endMinuteComboBox;

    @FXML
    private TextField locTextField;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<Integer> startHourComboBox;

    @FXML
    private ComboBox<Integer> startMinuteComboBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField appointmentIDTextField;

    @FXML
    void onClickSaveAppointment(ActionEvent event) {

    }

    @FXML
    void onClickToMainWindow(ActionEvent event) throws IOException {
        Utils.changeWindow(event, "../View/MainWindow.fxml", "Main Window");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer selCustomer = CustomerQuery.select(appointment.getCustomerName());
        Contact selContact = ContactQuery.select(appointment.getContactName());
        System.out.println(selCustomer.getName());
        System.out.println(selContact.getContactName());
        setCombos();

        appointmentIDTextField.setText(String.valueOf(appointment.getAppointmentID()));
        appointmentIDTextField.setDisable(true);

        custComboBox.setItems(CustomerQuery.selectAllToList());
        contactComboBox.setItems(ContactQuery.selectAllToList());

        custComboBox.valueProperty().addListener((options, oldValue, newValue) -> {
            if (newValue == null) {
                custIDTextField.clear();
            }
            else {
                Customer customer = custComboBox.getValue();
                custIDTextField.setText(String.valueOf(customer.getCustomerID()));
            }
        });
        titleTextField.setText(appointment.getTitle());
        typeTextField.setText(appointment.getType());
        descTextField.setText(appointment.getDescription());
        locTextField.setText(appointment.getLocation());
        custComboBox.getSelectionModel().select(selCustomer);
        contactComboBox.getSelectionModel().select(selContact);
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        String startTime = appointment.getStart().toLocalTime().toString();
        String[] splitStartTime = startTime.split(":");
        startHourComboBox.setValue(Integer.valueOf(splitStartTime[0]));
        startMinuteComboBox.setValue(Integer.valueOf(splitStartTime[1]));
        String endTime = appointment.getEnd().toLocalTime().toString();
        String[] splitEndTime = endTime.split(":");
        endHourComboBox.setValue(Integer.valueOf(splitEndTime[0]));
        endMinuteComboBox.setValue(Integer.valueOf(splitEndTime[1]));

//        startHourComboBox.setValue();
    }

    public void setCombos(){
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for(int i = 1; i <= 24; i++){
            hours.add(i);
        }
        for(int i = 0; i < 60; i++){
            minutes.add(i);
        }
        startHourComboBox.setItems(hours);
        endHourComboBox.setItems(hours);
        startMinuteComboBox.setItems(minutes);
        endMinuteComboBox.setItems(minutes);
    }
}
