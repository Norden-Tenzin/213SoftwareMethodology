package Project3.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Payroll processing method which will be used to take in input and process
 * output. Primarily focuses on P,F,M,R,C,S,Q,PA,PD,PH commands.
 * 
 * @Tenzin Norden, @Vedant Mehta
 */
public class PayrollProcessing {
   private Company company = new Company();
   private String output = "";

   /**
    * exportToFile method is used to export the database from the company class. it
    * calls the company.exportDatabase() method
    * 
    * @param filename String object
    */
   public void exportToFile(String filename) throws FileNotFoundException {
      company.exportDatabase(filename);
   }

   /**
    * importFile method imports the file and extracts the commands from the file.
    * It then takes the those commands and passes them to the run method.
    * 
    * @param importFile File object
    * @return String made up of outputs
    */
   public String importFile(File importFile) {
      Scanner sc;
      String output = "";

      try {
         sc = new Scanner(importFile);
         while (sc.hasNext()) {
            StringTokenizer input = new StringTokenizer(sc.nextLine(), ",");

            String command = "";
            String name = "";
            String lastName = "";
            String firstName = "";
            String department = "";
            String date = "";
            String payHours = "";
            String role = "";

            if (input.hasMoreTokens())
               command = input.nextToken();
            if (input.hasMoreTokens()) {
               name = input.nextToken();
               firstName = name.split(" ")[0];
               lastName = name.split(" ")[1];
            }
            if (input.hasMoreTokens())
               department = input.nextToken();
            if (input.hasMoreTokens())
               date = input.nextToken();
            if (input.hasMoreTokens())
               payHours = input.nextToken();
            if (input.hasMoreTokens())
               role = input.nextToken();

            String extracted = command + " " + lastName + "," + firstName + " " + department + " " + date + " "
                  + payHours + " " + role;
            output = run(extracted);
         }
      } catch (FileNotFoundException e) {
         output += "File Not Found";
      }
      return output;
   }

   /**
    * Run method used to run PayrollProcessing.It takes in the command line as an
    * argument.
    * 
    * @param inputCommands String object
    * @return String made up of outputs
    */
   public String run(String inputCommands) {
      StringTokenizer input = new StringTokenizer(inputCommands, " ");
      String command = "";
      String name = "";
      String department = "";
      String date = "";
      String payHours = "";
      String role = "";
      int totalInputs = input.countTokens();
      Profile employeeProfile = null;

      // Check for valid command
      if (input.hasMoreTokens()) {
         command = input.nextToken();
         String[] commands = { "P", "F", "M", "R", "C", "S", "P", "Q", "PA", "PD", "PH", };
         boolean isValidCommand = false;
         for (int i = 0; i < commands.length; i++) {
            if (command.equals(commands[i])) {
               isValidCommand = true;
               break;
            }
         }
         if (!isValidCommand) {
            output += "Command '" + command + "' not supported!" + "\n";
         }
      }

      // get tokenized input
      if (input.hasMoreTokens()) {
         name = input.nextToken();
      }
      if (input.hasMoreTokens()) {
         department = input.nextToken();
      }
      if (input.hasMoreTokens()) {
         date = input.nextToken();
      }
      if (input.hasMoreTokens()) {
         payHours = input.nextToken();
      }
      if (input.hasMoreTokens()) {
         role = input.nextToken();
      }

      boolean onlyOneArgument = totalInputs == 1;

      // create an employee profile if a date exists.
      if (date.length() > 0) {
         employeeProfile = new Profile(name, department, new Date(date));
         if (!isValidProfile(employeeProfile))
            return output;
      }
      if (!onlyOneArgument) {
         switch (command) {
         case "P":
            if (validatePayHours(payHours)) {
               Parttime employee = new Parttime(employeeProfile, Double.parseDouble(payHours));
               if (!company.alreadyExists(employee)) {
                  company.add(employee);
                  output += "Employee added." + "\n";
               } else {
                  output += "Employee is already in the list." + "\n";
               }
            }
            break;
         case "F":
            if (validatePayHours(payHours)) {
               Fulltime employee = new Fulltime(employeeProfile, Double.parseDouble(payHours));
               if (!company.alreadyExists(employee)) {
                  company.add(employee);
                  output += "Employee added." + "\n";
               } else {
                  output += "Employee is already in the list." + "\n";
               }
            }
            break;
         case "M":
            if (!validRole(role)) {
               output += "invalid Management code." + "\n";
               break;
            }
            if (validatePayHours(payHours) && validRole(role)) {
               Management employee = new Management(employeeProfile, Double.parseDouble(payHours),
                     Integer.parseInt(role));

               if (!company.alreadyExists(employee)) {
                  company.add(employee);
                  output += "Employee added." + "\n";
               } else {
                  output += "Employee is already in the list." + "\n";
               }
            }
            break;
         case "R":
            Employee removeEmployee = new Employee(employeeProfile);
            boolean wasRemoved = company.remove(removeEmployee);
            if (wasRemoved)
               output += "Employee removed." + "\n";
            else if (company.getNumEmployee() == 0) {
               output += "Employee database is empty." + "\n";
            } else
               output += "Employee does not exist." + "\n";
            break;
         case "S":
            if (company.getNumEmployee() == 0) {
               output += "Employee database is empty." + "\n";
               break;
            } else if (!validatePayHours(payHours)) {
               output += "Working hours cannot be negative." + "\n";
               break;
            } else if (Integer.parseInt(payHours) > Parttime.OVERFLOWHOURS) {
               output += "Invalid Hours: over 100." + "\n";
               break;
            } else if (validatePayHours(payHours)) {
               Parttime emp = new Parttime(employeeProfile, 0);
               emp.setHours(Integer.parseInt(payHours));
               if (company.setHours(emp))
                  output += "Working hours set." + "\n";
               else {
                  if (!company.alreadyExists(new Employee(employeeProfile))) {
                     output += "Employee does not exist." + "\n";
                  } else
                     output += "Employee is not a part-time employee." + "\n";
               }
            }
            break;
         }
      }

      // When we only have one argument passed:
      String printOutout = "";

      if (onlyOneArgument) {
         switch (command) {
         case "PA":
            if (company.getNumEmployee() == 0)
               output += "Employee database is empty." + "\n";
            else {
               printOutout += "--Printing earning statements for all employees--" + "\n";
               printOutout += company.print();
               return printOutout;
            }
            break;
         case "PD":
            if (company.getNumEmployee() == 0)
               output += "Employee database is empty." + "\n";
            else {
               printOutout += "--Printing earning statements for all employees--" + "\n";
               printOutout += company.printByDepartment();
               return printOutout;
            }
            break;
         case "PH":
            if (company.getNumEmployee() == 0)
               output += "Employee database is empty." + "\n";
            else {
               printOutout += "--Printing earning statements for all employees--" + "\n";
               printOutout += company.printByDate();
               return printOutout;
            }
            break;
         case "C":
            if (company.getNumEmployee() != 0) {
               company.processPayments();
               output += "Calculation of employee payments is done." + "\n";
            } else if (company.getNumEmployee() == 0) {
               output += "Employee database is empty." + "\n";
            }
            break;
         }
      }
      return output;
      // add(output, "Payroll Processing complete.");
   }

   /**
    * Validates the profile of an employee by checking date and department
    * parameters.
    *
    * @param profile of the employee.
    * @return true if valid, false otherwise
    */
   public boolean isValidProfile(Profile profile) {
      String date = profile.getDateHired().toString();
      if (!profile.validateDate()) {
         output += date + " is not a valid date!" + "\n";
         return false;
      }
      String department = profile.getDepartment();
      if (!profile.validateDepartment()) {
         output += department + " is not a valid department code." + "\n";
         return false;
      }
      return true;
   }

   /**
    * Validates the hourly pay of an employee by checking if it is negative
    *
    * @param payHours passed as a String which equal the hourly pay.
    * @return true if the hourly pay is valid, false otherwise.
    */
   public boolean validatePayHours(String payHours) {
      if (Double.parseDouble(payHours) < 0)
         return false;
      return true;
   }

   /**
    * Validates the number of arguments for any given command, excluding single
    * arguement commands. AP,AF,S all require 5 arguments. R requires 4 arguments.
    * AM requires 6 arguments.
    *
    * @param command is the command passed in(AP,AF,S,R,AM).
    * @param count   is the total number of arguments passed.
    * @return true if the count is equal to the expected argument count. False
    *         otherwise.
    */
   public boolean validateArguments(String command, int count) {
      int REMOVE_ARGS = 4; // standard number of args for removal
      int DEFAULT_ARGS = 5; // standard number of args for operations
      int MANAGER_ARGS = 6; // number of args for adding a mana
      boolean result = false;
      switch (command.trim()) {
      case "AP":
         result = count == DEFAULT_ARGS;
         break;
      case "AF":
         result = count == DEFAULT_ARGS;
         break;
      case "AM":
         result = count == MANAGER_ARGS;
         break;
      case "R":
         result = count == REMOVE_ARGS;
         break;
      case "S":
         if (count >= REMOVE_ARGS) {
            result = true;
         }
         break;
      default:
         result = false;
      }
      return result;
   }

   /**
    * Validates the manager role by checking if the Parsed integer from the String
    * paramter is between 0 and 3.
    *
    * @param role is the String value passed as an argument.
    * @return true if the parsed Integer from the String is between 0 and 3. False
    *         if otherwise.
    */
   public boolean validRole(String role) {
      if (Integer.parseInt(role) < 1 || Integer.parseInt(role) > 3) {
         return false;
      }
      return true;
   }
}
