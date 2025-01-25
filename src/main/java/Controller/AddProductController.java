package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the AddProductController class, and it controls all elements on the add product screen.<br> It allows the user to
 * enter in data for a new product and to save this information by creating a new Product object.<br> This screen contains a
 * topProduct table which contains all current available parts in the management system.<br> The user can select parts from
 * this table and click the add button which will add that part to the associatedPartsTable below.<br> The user can select an
 * associated part from the associatedPartsTable and click the remove associated part button to remove that part from the
 * table.<br> The user can then press save button which will save that product to the allProducts array list in the Inventory class,
 * as well as saving all the parts in the associatedPartsTable to the associatedParts array list in the product class.<br>
 * If the user clicks cancel it will prompt the user that all data entered will be erased without saving and if ok is clicked,
 * the user will be returned to the main form page.<br>
 */
public class AddProductController implements Initializable {
    /**
     * Array list that will contain the associated parts added into the associatedPartsTable.<br> This list will be passed over
     * to be added into the associatedPartsTable in the Product class and will be referenced for the instance of product
     * being created.
     */
    private static ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();

    /**
     * Private String variable that is initialized to null at first.<br> It will be reinitialized to the current text field's name
     * later during the input validation checks for null values in the text fields.
     */
    private String blankError = "";

    /**
     * Variable that contains the unique ID value for each product.<br><br> It is initialized to 999 initially,
     * because when the first product is added in the saveProduct method, productId is incremented by 1 making the
     * first product id value 1000.
     */
    public static int productId = 999;

    public TableView<Part> topProductTable;
    public TableView<Part> associatedPartsTable;
    public TableColumn topPartIDCol;
    public TableColumn topPartNameCol;
    public TableColumn topInvCol;
    public TableColumn topPriceCostCol;
    public TableColumn AssociatedPartIDCol;
    public TableColumn AssociatedPartNameCol;
    public TableColumn AssociatedPartInvCol;
    public TableColumn AssociatedPartPriceCostCol;
    public TextField productPartSearchTxt;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField invTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField minTxt;


    /**
     * This method is the override initialize method that is called first once the addProductController is called.<br> This
     * method populates the available parts table with the allParts array list from Inventory.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        topProductTable.setItems(Inventory.getAllParts());

        topPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This method displays an error alert dialog box letting the user know a text field is left blank.
     */
    public void blankTextError(){
        Alert alert = new Alert(Alert.AlertType.ERROR, blankError + " value field is blank, please enter in a value.");
        alert.setTitle("Products");
        alert.setHeaderText("Empty Text Field Error ");
        alert.showAndWait();
    }

    /**
     * This method adds the selected part from the top parts table into the bottom associated parts table.<br> It adds the
     * parts added into the associatedPartsTable into the productAssociatedParts array list which then will later be
     * added into the Product AssociatedParts array list.
     * @param actionEvent - When the add button is clicked below the top parts table.
     */
    public void onAddAssociatedPart(ActionEvent actionEvent) {

        /**
         * Variable of type Part that is used to hold the selected part's information before adding it into the bottom
         * associated parts table.
         */
        Part selectedAssociatedPart = topProductTable.getSelectionModel().getSelectedItem();

        if(selectedAssociatedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part selected, please select a part to add to the associated parts table.");
            alert.setTitle("Products");
            alert.showAndWait();
        }
        else{
            productAssociatedParts.add(selectedAssociatedPart);
            associatedPartsTable.setItems(getAssociatedPartsTable());

            AssociatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            AssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            AssociatedPartPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * This method removes the selected associated part from the bottom associated part tableview.
     * @param actionEvent - When the remove associated button is clicked.
     */
    public void RemovePart(ActionEvent actionEvent) {
        Part removeAssociatedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if(removeAssociatedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No part selected, please select a part to be removed from the associated parts table.");
            alert.setTitle("Products");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the selected associated part?");
            alert.setTitle("Products");
            alert.setHeaderText("Confirm Removal of Associated Part");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()&& result.get() == ButtonType.OK){
                associatedPartsTable.getItems().remove(removeAssociatedPart);
            }
        }
    }

    /**
     * This method saves the Product object to the Inventory allProducts arraylist.<br> The parts added into the
     * associated parts table will be added into the Product AssociatedParts array list.<br> There are multiple input
     * validations checking for blank text fields,incorrect data types, and logic errors for the inv, min, and max
     * text field values.<br> The method returns the user to the main form after the save button is clicked.<br>
     * @param actionEvent -  When the user clicks the save button.
     * @throws IOException - When the user enters a String value inside a text field expecting a numerical value.
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        // Collects the data from the text fields as a string

        /**
         * Variable that retrieves and contains the value inputted into the name text field as a String.
         */
        String nameS = nameTxt.getText();

        /**
         * Variable that retrieves and contains the value inputted into the inv text field as a String.
         */
        String inventoryS = invTxt.getText();

        /**
         * Variable that retrieves and contains the value inputted into the price text field as a String.
         */
        String priceS = priceTxt.getText();

        /**
         * Variable that retrieves and contains the value inputted into the max text field as a String.
         */
        String maxS = maxTxt.getText();

        /**
         * Variable that retrieves and contains the value inputted into the min text field as a String.
         */
        String minS = minTxt.getText();


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

        //Declares and sets variables for all number text fields
        /**
         * Variable that will contain the integer value after the String variable for inventory is parsed.
         */
        int inventory = 0;

        /**
         * Variable that will contain the integer value after the String variable for price is parsed.
         */
        double price = 0;

        /**
         * Variable that will contain the integer value after the String variable for max is parsed.
         */
        int max = 0;

        /**
         * Variable that will contain the integer value after the String variable for min is parsed.
         */
        int min = 0;

        /**
         * A variable that is null at first.<br> It will be reinitialized with the current
         * label of the text field that is blank for each error message during the saveProduct method.
         */
        String error = "";

        try {
            error = "Inv";
            inventory = Integer.parseInt(inventoryS);
            error = "Price/Cost";
            price = Double.parseDouble(priceS);
            error = "Max";
            max = Integer.parseInt(maxS);
            error = "Min";
            min = Integer.parseInt(minS);
            error = "Machine Id";


            //Input validation on the inv variable(Stock) to make sure it is in the bounds of min and max
            if(min >= max){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value should be less than the max value.");
                alert.setTitle("Products");
                alert.showAndWait();
                return;
            }
            if((inventory < min) || (inventory > max)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv value should be between the min and max values");
                alert.setTitle("Products");
                alert.showAndWait();
                return;
            }

            //Increments Id by 1, creates a new Product, and adds this new Product to the allProducts list.
            productId = 1 + productId;

            /**
             * Variable P is an object of Product that will be added to Inventory class's array list allProducts.
             */
            Product P = new Product(productId, nameS, price, inventory, min, max);

            Inventory.addProduct(P);


            // Adds the parts from the associated parts table to this product
            //Uses an instance of Product to call the addAssociatedParts method in the Product class. This is done
            // because that method and class is non-static.
            for (Part part:productAssociatedParts) {
                P.addAssociatedPart(part);
            }


        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, error + " " + "value must be a number!");
            alert.setTitle("Products");
            alert.showAndWait();
            System.out.println(error + " " + "value must be a number!");
            return;
        }

        //Returns user to the main form page after clicking the save button to add the inputted part information
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/c486/View/MainForm.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1500, 600);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This method extracts the input data from the search field and calls the lookUpPart method from the Inventory class
     * and searches through the array list for a match and returns the results to the partTable.<br>
     * If no results are found an error message is displayed saying no results are found.<br>
     * @param actionEvent - When the user presses enter when the focus is on the search text field.
     */
    public void onProductPartSearch(ActionEvent actionEvent) {
        /**
         * Variable that extracts and contains the value entered into the search text field.
         */
        String search = productPartSearchTxt.getText();

        /**
         * Array list that contains any part matches from the Inventory allParts array list.
         */
        ObservableList<Part> parts = Inventory.lookUpPart(search);

        try {
            int Id = Integer.parseInt(search);
            Part searchId = Inventory.lookUpPart(Id);
            if (searchId != null) {
                parts.add(searchId);
            }
            else {
                parts = Inventory.getAllParts();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part id not found!");
                alert.setTitle("Products");
                alert.setHeaderText("Search Error");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException e) {
            parts = Inventory.lookUpPart(search);
            if (parts.size()==0){
                parts = Inventory.getAllParts();
                Alert alert = new Alert(Alert.AlertType.ERROR, "No results found from this search criteria!");
                alert.setTitle("Products");
                alert.setHeaderText("Search Error");
                alert.showAndWait();
            }
        }
        topProductTable.setItems(parts);
    }

    /**
     * This method retrieves and returns the productAssociatedParts array list.
     * @return - The productAssociatedParts array list.
     */
    public static ObservableList <Part> getAssociatedPartsTable(){ return productAssociatedParts; }

    /**
     * This method confirms with the user before returning the user to the main form if the Cancel button is clicked.
     * @param actionEvent - Cancel button is clicked.
     * @throws IOException - When the fxml file cannot be found.
     */
    public void cancelToMain(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Canceling will erase all text field data " +
                "without saving, do you want to continue?");
        cancelAlert.setTitle("Products");
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
