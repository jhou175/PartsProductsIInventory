package Controller;

import Model.Inventory;
import Model.Product;
import Model.Part;
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
 * This is the ModifyProductController class that enables the user to modify an existing product's data.<br> It can do everything
 * the AddProductController class can do, but uses a different method to update the existing products' data.<br> Once the user
 * selects a product to modify from the parts table on the main form screen and clicks the modify button, they will be
 * redirected to this screen.<br> All the text fields will automatically populate with that products' information.
 */
public class ModifyProductController implements Initializable {

    /**
     * Array list that holds any associated parts added into the associated parts table.
     */
    private static ObservableList<Part> productModAssociatedParts = FXCollections.observableArrayList();

    /**
     * This variable is initialized as null at first.<br> It will be reinitialized during the input validation checks with
     * the current blank text field's label.<br> This variable will be used in the blankTextError method.
     */
    public String blankError;

    /**
     * This variable will contain the selected product's index in the allProducts array list.
     */
    static int selectedIndex;

    /**
     * This Product variable enables the two product controllers to communicate and transfer data of the selected product
     * being modified.<br> It will be reinitialized with the selected product object to be modified.
     */
    Product product;

    public TableView<Part> modifyProductPartsTable;
    public TableView<Part> modifyAssociatedPartsTable;

    public TextField idTxt;
    public TextField nameTxt;
    public TextField invTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField minTxt;
    public TextField modifySearchTxt;
    public TableColumn topPartIDCol;
    public TableColumn topPartNameCol;
    public TableColumn topInvCol;
    public TableColumn topPriceCostCol;
    public TableColumn AssociatedPartIDCol;
    public TableColumn AssociatedPartNameCol;
    public TableColumn AssociatedInvCol;
    public TableColumn AssociatedPriceCostCol;


    /**
     * This method is the first method called when the modify product screen is opened.<br> The method populates the parts
     * table view at the top of the screen with all available parts in the inventory system.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductPartsTable.setItems(Inventory.getAllParts());

        topPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method displays an error alert dialog box letting user know a text field is left blank.
     */
    public void blankTextError(){
        Alert alert = new Alert(Alert.AlertType.ERROR, blankError + " value field is blank, please enter in a value.");
        alert.setTitle("Products");
        alert.setHeaderText("Empty Text Field Error ");
        alert.showAndWait();
    }

    /**
     * This method is used to search the allParts array list with the value entered into the search text field at the top
     * of the screen.<br> The method will either return the part matching the id entered, or the part matching the full or
     * partial name given.<br> If no part is found an error message will be displayed.
     * @param actionEvent - When the enter key is pressed when the focus is on the search text field.
     */
    public void onModifySearchParts(ActionEvent actionEvent) {

        /**
         * Variable that extracts and contains the value entered into the search text field.
         */
        String search = modifySearchTxt.getText();

        /**
         * Array list that contains any part matches from the Inventory allParts array list.
         */
        ObservableList<Part> parts = Inventory.lookUpPart(search);

        try {
            int Id = Integer.parseInt(search);
            Part searchId = Inventory.lookUpPart(Id);
            if (searchId != null) {
                parts.add(searchId);
            } else {
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
        modifyProductPartsTable.setItems(parts);
    }

    /**
     * This method populates the texts fields in the modify product screen with the selected product's information.<br> It also gets
     * the index of the selected product and initializes the static variable selectedIndex to be used with updateProduct method.
     * @param selectedProduct - The product object selected by the user to be modified.
     */
    public void getSelectedProduct(Product selectedProduct) {
        product = selectedProduct;
        productModAssociatedParts =  FXCollections.observableArrayList();
        selectedIndex = Inventory.getAllProducts().indexOf(selectedProduct);

        idTxt.setText(String.valueOf(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        invTxt.setText(String.valueOf(selectedProduct.getStock()));
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        maxTxt.setText(String.valueOf(selectedProduct.getMax()));
        minTxt.setText(String.valueOf(selectedProduct.getMin()));

        modifyAssociatedPartsTable.setItems(selectedProduct.getAssociatedParts());
        productModAssociatedParts.addAll(selectedProduct.getAssociatedParts());
        AssociatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method adds the selected part from the top parts table into the bottom associated parts table.<br> It adds the
     * parts added into the associatedPartsTable into the Inventory associatedParts array list using the addAssociatedPart
     * method.
     * @param actionEvent - When the add button is clicked.
     * @throws IOException - If the user clicks add but does not select a part to be added.
     */
    public void addModifyAssociatedPart(ActionEvent actionEvent) throws IOException {
        Part selectedAssociatedPartMod = modifyProductPartsTable.getSelectionModel().getSelectedItem();


        if(selectedAssociatedPartMod == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No part selected, please select a part to add to the associated parts table.");
            alert.setTitle("Products");
            alert.showAndWait();
        }
        else{
            product.addAssociatedPart(selectedAssociatedPartMod);
            }
    }

    /**
     * This method removes the selected part from the Inventory associatedParts array list after confirming removal is wanted.<br>
     * An error message will prompt the user if no associated part is selected before pressing the remove part button.
     * @param actionEvent - When the remove associated part button is clicked.
     */
    public void RemoveAssociatedPart(ActionEvent actionEvent) {

        /**
         * Variable of type Part that will contain the object of the selected part to be removed.
         */
        Part removeAssociatedPart = modifyAssociatedPartsTable.getSelectionModel().getSelectedItem();

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
                if(!product.deleteAssociatedPart(removeAssociatedPart)){
                    Alert nullAlert = new Alert(Alert.AlertType.WARNING, "Associated part was not removed");
                    nullAlert.setTitle("Parts");
                    nullAlert.setHeaderText("Delete Warning");
                    nullAlert.showAndWait();
                }
            }
        }
    }

    /**
     * This method does everything the saveProduct method does in the AddProductController class, but instead of using the
     * Inventory class's addProduct method, it uses the Inventory class's updateProduct method.<br> The selected product
     * variable data is replaced with the newly entered data with the set method.<br> This product variable is then passed
     * into the updateProduct method which replaces that instance of product with the newly modified product instance.<br>
     * The user is returned to the main form page once the save button is clicked.<br>
     * @param actionEvent - When the save button is clicked.
     * @throws IOException - When the user enters a String value inside a text field expecting a numerical value.
     */
    public void saveModifiedProduct(ActionEvent actionEvent) throws IOException {

        // Collects the data from the text fields as a string
        /**
         * Variable that retrieves and contains the value inputted into the Id text field as a String.
         */
        String productIds = idTxt.getText();

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
         * Variable that will contain the integer value after the String variable for Id is parsed.
         */
        int productId = 0;

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
         * label of the text field that is blank for each error message during the saveModifiedProduct method.
         */
        String error = "";

        try {
            productId = Integer.parseInt(productIds);
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

            //Replaces the old data for product with the newly entered data.
            product.setName(nameS);
            product.setStock(inventory);
            product.setPrice(price);
            product.setMax(max);
            product.setMin(min);

            //Passes the index of the selected product to be modified as well as the newly set product object.
            Inventory.updateProduct(selectedIndex, product);

        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, error + " " + "value must be a number!");
            alert.setTitle("Products");
            alert.showAndWait();
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
     * This method confirms with the user before returning the user to the main form if the Cancel button is clicked.
     * @param actionEvent - When the user clicks the cancel button.
     * @throws IOException - If the FXML file cannot be found.
     */
    public void cancelToMain(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Canceling will erase any changes made " +
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

