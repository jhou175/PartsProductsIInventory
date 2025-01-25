package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
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
 * This is the AddPartController that handles the add part form.<br> It allows the user to select between the radio buttons of
 * InHouse and Outsourced which will depict which subclass the part will go into.<br> The form has a save and cancel button.<br>
 * The save button will add the part into the allParts list and the cancel button will return the user to the main form page.<br>
 */
public class AddPartController {

    /**
     * A variable that is null at first. It will be reinitialized with the current
     * label of the text field that is blank for each error message during the onAddSavePart method.
     */
    static String blankError;

    /**
     * An integer variable that contains the unique part ID that will be incremented by 1
     * in the onAddPartSave method everytime a part is saved.<br> It is initialized to 0 so that the first part added will
     * begin at 1.
     */
    public static int partId = 0;

    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Label selectedOptionLbl;
    public TextField selectedOptionTxt;
    public TextField IDTxt;
    public TextField nameTxt;
    public TextField inventoryTxt;
    public TextField priceCostTxt;
    public TextField maxTxt;
    public TextField minTxt;

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
     * This method sets the selectedOption label to "Machine ID" if the InHouse radio button is selected.
     * @param actionEvent - InHouse radio button is selected.
     */
    public void onInHouse(ActionEvent actionEvent) {
        selectedOptionLbl.setText("Machine ID");
    }

    /**
     * This method sets the selectedOption label to "Company Name" if the Outsourced radio button is selected.
     * @param actionEvent - Outsourced radio button is selected.
     */
    public void onOutsourced(ActionEvent actionEvent) {
        selectedOptionLbl.setText("Company Name");
    }

    /**
     * This method retrieves the data from all text fields and initializes string variables with this data.<br>
     * These string variables are parsed into Integers and placed into the integer version of the original string variables.<br>
     * This method checks to make sure all text fields are not null and if they are, an error message is displayed.<br>
     * This method uses two different main if statements that check which radio button is selected and change which label
     * to display and which subclass to place the part object in and adds the part into the allParts array list.<br>
     * The method checks to make sure numerical text fields do not contain a string value and if they do, an error message is
     * displayed.<br> Returns the user to the main form page after the save button is pressed.<br>
     * <br>
     * <b> RUNTIME ERROR</b><br> An <i>incompatible type error</i> was occurring once addPart was being saved if the InHouse radiobutton was selected.<br>
     * I was trying to pass the string variable of selectedTextS into the new Inhouse I object.<br>
     * That object is expecting an integer value for machineId, so I parsed the string variable selectedTextS to an integer variable selectedTxt.<br>
     * @param actionEvent - Save part button is clicked.
     * @throws IOException - When the user enters a String value in a text field expecting a numerical value.
     */
    public void onAddPartSave(ActionEvent actionEvent) throws IOException {
        // Collects the data from the text fields as a string
        /**
         * String variable that retrieves part name.
         */
        String nameS = nameTxt.getText();

        /**
         * String variable that retrieves part inventory amount.
         */
        String inventoryS = inventoryTxt.getText();

        /**
         * String variable that retrieves part price amount.
         */
        String priceS = priceCostTxt.getText();

        /**
         * String variable that retrieves part maximum inventory amount.
         */
        String maxS = maxTxt.getText();

        /**
         * String variable that retrieves part minimum inventory amount.
         */
        String minS = minTxt.getText();

        /**
         * String variable that retrieves either the part's machine id or company name.<br> This is dependent on which radio
         * button is selected.
         */
        String selectedTxtS = selectedOptionTxt.getText();


        /**
         * Variable that holds the integer value of selectedTxt which will be Machine ID.
         */
        int selectedTxt = 0;

        /**
         * Variable that holds the integer value of inventory(stock).
         */
        int inventory = 0;

        /**
         * Variable that holds the integer value of price.
         */
        double price = 0;

        /**
         * Variable that holds the integer value of max.
         */
        int max = 0;

        /**
         * Variable that holds the integer value of min.
         */
        int min = 0;

        /**
         * Variable that will be reinitialized during the input validation with the current text field's name to check if
         * numerical text fields are entered with a String value.
         */
        String valueError = "";

        //Input validation on user inputs validating if inputs are blank
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


        // If statements increment the id variable by 1 to give each new part a new unique id number.
        // If statements adds a new part into the allParts observable list depending on which radio button is selected.
        // If statements convert the variables from the String text fields to integers and doubles and validates if a
        // number is entered.
        if (inHouseRadio.isSelected()) {
            try {
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

                //Input validation on the inv variable(Stock) to make sure it is in the bounds of min and max.
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
                //Increments partId by 1, creates new InHouse Part object, and adds this object to the allParts list.
                partId = 1 + partId;

                /**
                 * Variable I is a subclass object of InHouse for the superclass Part.<br> It will be added to the Inventory
                 * class's array list allParts.
                 */
                InHouse I = new InHouse(partId, nameS, price, inventory, min, max, selectedTxt);
                Inventory.addPart(I);
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, valueError + " " + "value must be a number!");
                alert.setTitle("Parts");
                alert.showAndWait();
                return;
            }
        }
        if (outsourcedRadio.isSelected()) {
            try {
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
                //Increments partId by 1, creates new Outsource Part object, and adds this object to the allParts list.
                partId = 1 + partId;

                /**
                 * Variable O is a subclass object of Outsource for the superclass Part.<br> It will be added to the Inventory
                 * class's array list allParts.
                 */
                Outsourced O = new Outsourced(partId, nameS, price, inventory, min, max, selectedTxtS);
                Inventory.addPart(O);

            } catch (NumberFormatException e) {
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
    public void onCancelToMain(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Canceling will erase all text field data " +
                "without saving, do you want to continue?");
        cancelAlert.setTitle("Parts");
        cancelAlert.setHeaderText("Confirm Cancel to Main Form");
        Optional<ButtonType> cancelResult = cancelAlert.showAndWait();

        if(cancelResult.isPresent()&& cancelResult.get()==ButtonType.OK){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                    ("/c486/View/MainForm.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1500, 600);
            stage.setScene(scene);
            stage.show();
        }
    }
}

