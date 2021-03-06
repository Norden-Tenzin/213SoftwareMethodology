package Project3.src;

import javax.swing.JRadioButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Controller class is used to control all the elements of the fxml. It handles
 * user interaction logic. Handles Add, Remove, SetHours, Import, Export and All
 * the print commands in UI.
 * 
 * @Tenzin Norden, @Vedant Mehta
 */
public class Controller {

   private PayrollProcessing pp = new PayrollProcessing();
   private File uploadedFile;
   @FXML
   private TabPane mangeEmployees_TabPane;

   @FXML
   private AnchorPane addPane;

   // Variables for add:
   @FXML
   private Tab addTab;

   @FXML
   private ButtonBar managerTypeButtonBar;

   @FXML
   private RadioButton managerTypeSelect_MNGR;

   @FXML
   private RadioButton managerTypeSelect_DH;

   @FXML
   private RadioButton managerTypeSelect_Dir;

   @FXML
   private TextField lastName_Input_add;

   @FXML
   private TextField firstName_Input_add;

   @FXML
   private RadioButton departmentSelect_CS_add;

   @FXML
   private RadioButton departmentSelect_ECE_add;

   @FXML
   private RadioButton departmentSelect_IT_add;

   @FXML
   private ButtonBar departmentButtonBar_add;

   @FXML
   private ButtonBar employeeButtonBar_add;

   @FXML
   private DatePicker dateHired_Input_add;

   @FXML
   private RadioButton employeeTypeSelect_MNGR_add;

   @FXML
   private RadioButton employeeTypeSelect_FT_add;

   @FXML
   private RadioButton employeeTypeSelect_PT_add;

   @FXML
   private TextField hourlyRate_Input;

   // Variables for remove:
   @FXML
   private Tab removeTab;

   @FXML
   private ButtonBar departmentButtonBar_RMV;

   @FXML
   private ButtonBar employeeButtonBar_RMV;

   @FXML
   private TextField lastName_Input_rmv;

   @FXML
   private TextField firstName_Input_rmv;

   @FXML
   private RadioButton departmentSelect_CS_rmv;

   @FXML
   private RadioButton departmentSelect_ECE_rmv;

   @FXML
   private RadioButton departmentSelect_IT_rmv;

   @FXML
   private DatePicker dateHired_Input_rmv;

   @FXML
   private RadioButton employeeTypeSelect_MNGR_rmv;

   @FXML
   private RadioButton employeeTypeSelect_FT_rmv;

   @FXML
   private RadioButton employeeTypeSelect_PT_rmv;

   // Variables for Set Hours:
   @FXML
   private Tab setHoursTab;

   @FXML
   private ButtonBar departmentButtonBar_SH;

   @FXML
   private TextField lastName_Input_sh;

   @FXML
   private TextField firstName_Input_sh;

   @FXML
   private RadioButton departmentSelect_CS_sh;

   @FXML
   private RadioButton departmentSelect_ECE_sh;

   @FXML
   private RadioButton departmentSelect_IT_sh;

   @FXML
   private DatePicker dateHired_Input_sh;

   @FXML
   private TextField numHours_Input;

   // Submit button
   @FXML
   private Button submit;

   @FXML
   private Button resetButton;

   // process payment button
   @FXML
   private Button processPayment;

   // error message output:
   @FXML
   private TextArea messageOuput;

   // For print by department etc output:
   @FXML
   private TextArea printOutput;

   // print buttons:
   @FXML
   private Button viewAll_Button;

   @FXML
   private Button viewDateHired_Button;

   @FXML
   private Button viewDepartment_Button;

   // import/export buttons:
   @FXML
   private Button uploadFile_Button;

   @FXML
   private Button submitImport;

   // message box for import:
   @FXML
   private TextArea importOutput;

   // export variables:
   @FXML
   private Button export_Button;

   @FXML
   private TextArea exportOutput;

   @FXML
   private Button quitButton;

   @FXML
   void onQuit(ActionEvent event) {
      Stage stage = (Stage) quitButton.getScene().getWindow();
      stage.close();
   }

   /**
    * Disables the Manager radio buttons.
    *
    * @param event
    */
   @FXML
   void disableManagerTypeSelect(ActionEvent event) {
      managerTypeButtonBar.setDisable(true);
   }

   /**
    * Disables the Department radio buttons.
    * 
    * @param event
    */
   @FXML
   void disableDepartmentSelect(ActionEvent event) {

      int tabIndex = mangeEmployees_TabPane.getSelectionModel().getSelectedIndex();
      switch (tabIndex) {
      case 0:
         departmentButtonBar_add.setDisable(true);
         break;
      case 1:
         departmentButtonBar_RMV.setDisable(true);
         break;
      case 2:
         departmentButtonBar_SH.setDisable(true);
         break;
      }
   }

   /**
    * Disables the Employee radio buttons.
    * 
    * @param event
    */
   @FXML
   void disableEmployeeSelect(ActionEvent event) {
      if (employeeTypeSelect_MNGR_add.isSelected()) {
         managerTypeButtonBar.setDisable(false);
      }
      int tabIndex = mangeEmployees_TabPane.getSelectionModel().getSelectedIndex();
      switch (tabIndex) {
      case 0:
         employeeButtonBar_add.setDisable(true);
         break;
      case 1:
         employeeButtonBar_RMV.setDisable(true);
         break;
      }

   }

   /**
    * Exports the Database into a text file.
    * 
    * @param event
    */
   @FXML
   void onExportSubmit(ActionEvent event) throws InvocationTargetException {

      try {
         exportOutput.clear();
         FileChooser chooser = new FileChooser();
         chooser.setTitle("Open Target File for the Export");
         chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
               new ExtensionFilter("All Files", "*.*"));
         Stage stage = new Stage();
         File targetFile = chooser.showSaveDialog(stage);
         try {
            pp.exportToFile(targetFile.getName());
            exportOutput.setText("Database exported to " + targetFile.getName());
         } catch (FileNotFoundException e) {
            exportOutput.setText("Unable to export the file - File not found");
         }
      } catch (Exception e) {
         exportOutput.setText("Please select a valid output file");
      }
   }

   /**
    * Function that gets triggered when the "Submit" button is pressed on the
    * import page. This calls the helper class to run an importFile function.
    * 
    * @param event
    */
   @FXML
   void onFileSubmit(ActionEvent event) {
      importOutput.setText(pp.importFile(uploadedFile));
   }

   /**
    * Function that gets triggered when the "Upload File" is pressed. This only
    * accepts .txt files.
    * 
    * @param event
    */
   @FXML
   void onFileUpload(ActionEvent event) throws InvocationTargetException {
      try {
         FileChooser chooser = new FileChooser();
         chooser.setTitle("Open Source File for the Import");
         chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
               new ExtensionFilter("All Files", "*.*"));
         Stage stage = new Stage();
         File sourceFile = chooser.showOpenDialog(stage);
         uploadFile_Button.setText(sourceFile.getName());
         if (sourceFile.exists()) {
            submitImport.setDisable(false);
            uploadedFile = sourceFile;
         }
      } catch (Exception e) {
         importOutput.setText("Please upload a valid file");
      }
   }

   /**
    * Function for Button ProcessPayment which processes the payment when clicked.
    * 
    * @param event
    */
   @FXML
   void onProcessPayment(ActionEvent event) {
      String input = "C";
      messageOuput.setText(reverseOutput(pp.run(input)));
   }

   /**
    * Function for Button Submit which creates the input command depending on the
    * inputs provided by the user. This function handles Add, Remove and Set Hours
    * Tab Functionality.
    * 
    * @param event
    */
   @FXML
   void onSubmit(ActionEvent event) throws InvocationTargetException {

      String finalOutput = "";

      String input = "";
      String command = "";
      String department = "";
      String managerType = "";
      String firstName = "";
      String lastName = "";
      String dateHired = "";
      String hourlyRate = "";
      String numHours = "";
      try {
         int tabIndex = mangeEmployees_TabPane.getSelectionModel().getSelectedIndex();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
         switch (tabIndex) {
         case 0:
            firstName = firstName_Input_add.getText();
            lastName = lastName_Input_add.getText();
            dateHired = formatter.format(dateHired_Input_add.getValue());
            hourlyRate = hourlyRate_Input.getText();

            if (departmentSelect_CS_add.isSelected())
               department = "CS";
            else if (departmentSelect_ECE_add.isSelected())
               department = "ECE";
            else if (departmentSelect_IT_add.isSelected())
               department = "IT";

            if (employeeTypeSelect_MNGR_add.isSelected())
               command = "M";
            else if (employeeTypeSelect_FT_add.isSelected())
               command = "F";
            else if (employeeTypeSelect_PT_add.isSelected())
               command = "P";

            if (managerTypeSelect_MNGR.isSelected())
               managerType = "1";
            else if (managerTypeSelect_DH.isSelected())
               managerType = "2";
            else if (managerTypeSelect_Dir.isSelected())
               managerType = "3";

            input = command + " " + lastName + "," + firstName + " " + department + " " + dateHired + " " + hourlyRate
                  + " " + managerType;
            break;

         case 1:
            firstName = firstName_Input_rmv.getText();
            lastName = lastName_Input_rmv.getText();
            dateHired = formatter.format(dateHired_Input_rmv.getValue());
            if (departmentSelect_CS_rmv.isSelected())
               department = "CS";
            else if (departmentSelect_ECE_rmv.isSelected())
               department = "ECE";
            else if (departmentSelect_IT_rmv.isSelected())
               department = "IT";

            input = "R " + lastName + "," + firstName + " " + department + " " + dateHired;
            break;

         case 2:
            firstName = firstName_Input_sh.getText();
            lastName = lastName_Input_sh.getText();
            dateHired = formatter.format(dateHired_Input_sh.getValue());

            if (departmentSelect_CS_sh.isSelected())
               department = "CS";
            else if (departmentSelect_ECE_sh.isSelected())
               department = "ECE";
            else if (departmentSelect_IT_sh.isSelected())
               department = "IT";

            numHours = numHours_Input.getText();

            input = "S " + lastName + "," + firstName + " " + department + " " + dateHired + " " + numHours;
            break;
         }
         messageOuput.setText(reverseOutput(pp.run(input)));
      } catch (Exception e) {
         messageOuput.setText("Please input all necessary values");
      }
   }

   /**
    * Function that Runs the Print All command and set that value to the textArea.
    * 
    * @param event
    */
   @FXML
   void onViewAll(ActionEvent event) {
      printOutput.clear();
      String input = "PA";

      printOutput.setText(pp.run(input));
   }

   /**
    * Function that Runs the Print By Date command and set that value to the
    * textArea.
    * 
    * @param event
    */
   @FXML
   void onViewDateHired(ActionEvent event) {
      printOutput.clear();

      String input = "PH";

      printOutput.setText(pp.run(input));
   }

   /**
    * Function that Runs the Print By Department command and set that value to the
    * textArea.
    * 
    * @param event
    */
   @FXML
   void onViewDepartment(ActionEvent event) {
      printOutput.clear();

      String input = "PD";

      printOutput.setText(pp.run(input));
   }

   /**
    * Initialize function is used to initialize default values.
    * 
    * @param event
    */
   @FXML
   public void initialize() {
      managerTypeButtonBar.setDisable(true);
      submitImport.setDisable(true);
   }

   /**
    * Function Cleans the input areas and allowing the user to input the new data.
    * 
    * @param event
    */
   @FXML
   void resetData(ActionEvent event) {
      lastName_Input_add.clear();
      lastName_Input_rmv.clear();
      lastName_Input_sh.clear();

      firstName_Input_add.clear();
      firstName_Input_rmv.clear();
      firstName_Input_sh.clear();

      dateHired_Input_add.getEditor().clear();
      dateHired_Input_rmv.getEditor().clear();
      dateHired_Input_sh.getEditor().clear();

      departmentSelect_CS_add.setSelected(false);
      departmentSelect_CS_rmv.setSelected(false);
      departmentSelect_CS_sh.setSelected(false);

      departmentSelect_ECE_add.setSelected(false);
      departmentSelect_ECE_rmv.setSelected(false);
      departmentSelect_ECE_sh.setSelected(false);

      departmentSelect_IT_add.setSelected(false);
      departmentSelect_IT_rmv.setSelected(false);
      departmentSelect_IT_sh.setSelected(false);

      employeeTypeSelect_FT_add.setSelected(false);
      employeeTypeSelect_PT_add.setSelected(false);
      employeeTypeSelect_MNGR_add.setSelected(false);

      managerTypeSelect_MNGR.setSelected(false);
      managerTypeSelect_DH.setSelected(false);
      managerTypeSelect_Dir.setSelected(false);

      managerTypeButtonBar.setDisable(true);
      departmentButtonBar_add.setDisable(false);
      departmentButtonBar_RMV.setDisable(false);
      departmentButtonBar_SH.setDisable(false);
      employeeButtonBar_add.setDisable(false);

      hourlyRate_Input.setText("");
      numHours_Input.setText("");
      uploadFile_Button.setText("Upload File");

      printOutput.clear();
      messageOuput.clear();
      exportOutput.clear();
      importOutput.clear();
   }

   /**
    * Reverses the output of the payroll processing run() method for the UI.
    * 
    * @param str output string of the run() method used by the payment processing
    *            class
    * @return the reversed string of the ouput string generated by the run()
    *         method.
    */
   public String reverseOutput(String str) {
      String outputString = "";
      String[] splittedString = str.split("\n");
      for (int i = splittedString.length - 1; i >= 0; i--) {
         outputString += splittedString[i] + "\n";
      }
      return outputString;
   }
}
