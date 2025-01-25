package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


/**
 * This is the ModifyPartController class that will enable the user to modify a selected part's data.<br> This class can do
 * everything the AddPartController class can do.<br> Once the user selects a part to modify from the parts table on the main
 * form screen and clicks the modify button, they will be redirected to this screen.<br> All the text fields will automatically
 * populate with that parts' information.
 */
public class ModifyPartController {

    /**
     * This variable will contain the selected part's index in the allParts array list.
     */
    static int selectedIndex;

    /**
     * This variable is initialized as null at first.<br> It will be reinitialized during the input validation checks with
     * the current blank text field's label.<br> This variable will be used in the blankTextError method.
     */
    static String blankError="";

    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Label selectedOptionLbl;
    public TextField IDTxt;
    public TextField nameTxt;
    public TextField inventoryTxt;
    public TextField priceCostTxt;
    public TextField maxTxt;
    public TextField selectedOptionTxt;
    public TextField minTxt;
    public Button saveButton;

    /**
     * This method populates the texts fields in the modify screen with the selected part's information.<br> It also gets
     * the index of the selected part and initializes the static variable selectedIndex to be used with updatePart method.
     * @param selectedPart - The part that was selected to be modified on the main form screen.
     */
    public void getSelectedPart(Part selectedPart){
        /**
         * This variable will retrieve and contain the index of the selected part in the allParts array list.
         */
        selectedIndex = Inventory.getAllParts().indexOf(selectedPart);

        IDTxt.setText(String.valueOf(selectedPart.getId()));
        nameTxt.setText(selectedPart.getName());
        inventoryTxt.setText(String.valueOf(selectedPart.getStock()));
        priceCostTxt.setText(String.valueOf(selectedPart.getPrice()));
        maxTxt.setText(String.valueOf(selectedPart.getMax()));
        minTxt.setText(String.valueOf(selectedPart.getMin()));


        if(selectedPart instanceof InHouse){
            inHouseRadio.setSelected(true);
            selectedOptionLbl.setText("Machine Id");
            selectedOptionTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));

        }
        else if (selectedPart instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            selectedOptionLbl.setText("Company Name");
            selectedOptionTxt.setText(((Outsourced)selectedPart).getCompanyName());
        }

    }

    /**
     * This method sets the selectedOption label to "Machine ID" if the In-House radio button is selected.
     * @param event - When the InHouse radio button is selected.
     */
    public void onInHouse(ActionEvent event) { selectedOptionLbl.setText("Machine ID"); }

    /**
     * This method sets the selectedOption label to "Company Name" if the Outsourced radio button is selected.
     * @param event - When the Outsourced radio button is selected.
     */
    public void onOutsourced(ActionEvent event) { selectedOptionLbl.setText("Company Name"); }

    /**
     * This method displays an alert error dialog box that tells user if a text field is left blank.
     */
    public void blankTextError(){
        Alert blankAlert = new Alert(Alert.AlertType.ERROR, blankError + " value field is blank, please enter in a value.");
        blankAlert.setTitle("Parts");
        blankAlert.setHeaderText("Empty Text Field Error");
        blankAlert.showAndWait();
    }

    /**
     * This method does everything the savePart method does in the AddPartController class, but instead of using the
     * Inventory class's addPart method, it uses the Inventory updatePart method.<br> This passes the selectedParts index as
     * well as the newly created part into the updatePart method and replaces the old object with the new modified object.<br>
     * Method will return the user to the main form screen after clicking the save button.<br>
     * @param actionEvent - When the user clicks the save button.
     * @throws IOException - When the user enters a String value in a text field expecting a numerical value.
     */
    public void saveModifyPart(ActionEvent actionEvent) throws IOException {
        /**
         * String variable that retrieves part Id.
         */
        String partIdS = IDTxt.getText();

        /**
         * String variable that retrieves part name.
         */
        String nameS = nameTxt.getText();

        /**
         * String variable that retrieves part inventory amount.
         */
        String inventoryS = inventoryTxt.getText();

        /**
         * String variable that retrieves part price.
         */
        String priceS = priceCostTxt.getText();

        /**
         * String variable that retrieves part max inventory amount
         */
        String maxS = maxTxt.getText();

        /**
         * String variable that retrieves part min inventory amount.
         */
        String minS = minTxt.getText();

        /**
         * String variable that retrieves either the part's machine id or company name.<br> This is dependent on which radio
         * button is selected.
         */
        String selectedTxtS = selectedOptionTxt.getText();

        //Declares and sets variables for all number type text fields and to set error String variables to null
        /**
         * Variable that holds the integer value of part Id.
         */
        int retainedID = 0;

        /**
         *  Variable that holds the integer value of selectedTxt which will be Machine ID.
         */
        int selectedTxt = 0;

        /**
         * Variable that holds the integer value of part inventory.
         */
        int inventory = 0;

        /**
         * Variable that holds the integer value of part price.
         */
        double price = 0;

        /**
         * Variable that holds the integer value of part max.
         */
        int max = 0;

        /**
         * Variable that holds the integer value of part min.
         */
        int min = 0;

        /**
         * Variable that will be reinitialized during the input validation with the current text field's name to check if
         * numerical text fields are entered with a String value.
         */
        String valueError = "";


        //Input validation checking if text fields are left blank
        if (nameS.isBlank()) {
            blankError = "Name";
            blankTextError();
            return;
        }

        if (inventoryS.isBlank()) {
            blankError = "Inv";
            blankTextError();
            return;
        }

        if (priceS.isBlank()) {
            blankError = "Price/Cost";
            blankTextError();
            return;
        }

        if (maxS.isBlank()) {
            blankError = "Max";
            blankTextError();
            return;
        }

        if (minS.isBlank()) {
            blankError = "Min";
            blankTextError();
            return;
        }

        if (outsourcedRadio.isSelected()) {
            if (selectedTxtS.isBlank()) {
                blankError = "Company Name";
                blankTextError();
                return;
            }
        }
        if (inHouseRadio.isSelected()) {
            if (selectedTxtS.isBlank()) {
                blankError = "Machine ID";
                blankTextError();
                return;
            }
        }

        // If statements increment the id variable by 2 to give each new product a new unique even id number
        // If statements adds a new part into the allParts observable list depending on which radio button is selected.
        // If statements convert the variables from the String text fields to integers and doubles and validates if a
        // number is entered
        if (inHouseRadio.isSelected()) {
            try {
                retainedID = Integer.parseInt(partIdS);
                valueError = "Inv";
                inventory = Integer.parseInt(inventoryS);
                valueError = "Price/Cost";
                price = Double.parseDouble(priceS);
                valueError = "Max";
                max = Integer.parseInt(maxS);
                valueError = "Min";
                min = Integer.parseInt(minS);
                valueError = "Machine Id";
                selectedTxt = Integer.parseInt(selectedTxtS);

                //Input validation on the inv variable(Stock) to make sure it is in the bounds of min and max
                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min value should be less than the max value.");
                    alert.setTitle("Parts");
                    alert.showAndWait();
                    return;
                }
                if ((inventory < min) || (inventory > max)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inv value should be between the min and max values");
                    alert.setTitle("Parts");
                    alert.showAndWait();
                    return;
                }

                /**
                 * Variable I is a subclass object of InHouse for the superclass Part.<br> It will be passed into the Inventory
                 * class's updatePart method.
                 */
                InHouse I = new InHouse(retainedID, nameS, price, inventory, min, max, selectedTxt);

                Inventory.updatePart(selectedIndex,I);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, valueError + " " + "value must be a number!");
                alert.setTitle("Parts");
                alert.showAndWait();
                return;
            }
        }
        if (outsourcedRadio.isSelected()) {
            try {
                retainedID = Integer.parseInt(partIdS);
                valueError = "Inv";
                inventory = Integer.parseInt(inventoryS);
                valueError = "Price/Cost";
                price = Double.parseDouble(priceS);
                valueError = "Max";
                max = Integer.parseInt(maxS);
                valueError = "Min";
                min = Integer.parseInt(minS);

                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Min value should be less than the max value.");
                    alert.showAndWait();
                    System.out.println("Min should be less than the max value.");
                    return;
                }
                if ((inventory < min) || (inventory > max)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inv value should be between the min and max values");
                    alert.showAndWait();
                    System.out.println("Inv value should be a value between min and max");
                    return;
                }

                /**
                 * Variable O is a subclass object of Outsource for the superclass Part.<br> It will be passed into the Inventory
                 * class's updatePart method.
                 */
                Outsourced O = new Outsourced(retainedID, nameS, price, inventory, min, max, selectedTxtS);
                Inventory.updatePart(selectedIndex,O);

            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, valueError + " " + "value must be a number!");
                alert.setTitle("Parts");
                alert.showAndWait();
                return;
            }
        }

        //Returns user to the main form page after clicking the save button to add the inputted part information
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/c486/View/MainForm.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1500, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method confirms with the user before returning the user to the main form if the Cancel button is clicked.
     * @param actionEvent - Cancel button is clicked.
     * @throws IOException - When the FXML file cannot be found.
     */
    public void cancelToMain(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Canceling will erase any changes made " +
                "without saving, do you want to continue?");
        cancelAlert.setTitle("Parts");
        cancelAlert.setHeaderText("Confirm Cancel to Main Form");
        Optional<ButtonType> cancelResult = cancelAlert.showAndWait();

        if(cancelResult.isPresent()&& cancelResult.get()==ButtonType.OK){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/c486/View/MainForm.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1500, 600);
            stage.setScene(scene);
            stage.show();
        }
    }
}

