package Project3.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Tab addTab;

    @FXML
    private TextField lastName_Input;

    @FXML
    private TextField firstName_Input;

    @FXML
    private RadioButton departmentSelect_CS;

    @FXML
    private RadioButton departmentSelect_ECE;

    @FXML
    private RadioButton departmentSelect_IT;

    @FXML
    private DatePicker dateHired_Input;

    @FXML
    private RadioButton employeeTypeSelect_MNGR;

    @FXML
    private RadioButton employeeTypeSelect_FT;

    @FXML
    private RadioButton employeeTypeSelect_PT;

    @FXML
    private TextField hourlyRate_Input;

    @FXML
    private Tab removeTab;

    @FXML
    private Tab setHoursTab;

    @FXML
    private TextField numHours_Input;

    @FXML
    private Button submit;

    @FXML
    private Button processPayment;

    @FXML
    private Button viewAll_Button;

    @FXML
    private Button viewDateHired_Button;

    @FXML
    private Button viewDepartment_Button;

    @FXML
    private Button uploadFile_Button;

    @FXML
    private Button submitImport;

    @FXML
    private Button export_Button;

    @FXML
    void onSubmit(ActionEvent event) {
      System.out.println("Yo");
      if(departmentSelect_CS.isPressed()){
         String toogleGroupValue = departmentSelect_CS.getText();
         System.out.println(toogleGroupValue + "was selected");
      }
      else System.out.println("nothing selected");
    }

}