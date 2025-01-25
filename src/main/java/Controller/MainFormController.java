package Controller;

import Model.Inventory;
import Model.Product;
import Model.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the MainFormController class that implements Initializable which populates both parts and products tables with test data.<br>
 * This is the starting point of the program and has buttons that lead to all the other forms of the program.<br>
 */
public class MainFormController implements Initializable {

    public TableView<Part> partsTable;
    public TableView<Product> productsTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCostCol;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvLevelCol;
    public TableColumn productPriceCostCol;
    public TextField partSearchTxt;
    public TextField productsSearchTxt;

    /**
     * The initialize method that will be called first when the main form opens.<br> This method handles setting the table's
     * data to the allParts and allProducts array lists contents.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method extracts the input from the search field and calls the lookUpPart method from the Inventory class
     * and searches through the array list for a match and returns the results to the partTable.<br>
     * If no results are found an error message is displayed saying no results are found.<br>
     * @throws NumberFormatException
     */
    public void getPartSearchResults() throws NumberFormatException {
        /**
         * String variable that retrieves the input from the part search text field.
         */
        String search = partSearchTxt.getText();

        /**
         * Array list of type Part that contains any string matches from the inputted search criteria.
         */
        ObservableList<Part> parts = Inventory.lookUpPart(search);

        try {
            /**
             * Variable that parses the string search variable from the search text field to get the integer id.
             */
            int Id = Integer.parseInt(search);

            /**
             * Array list of type Part that contains any integer matches from the inputted search criteria.
             */
            Part searchId = Inventory.lookUpPart(Id);

            if (searchId != null) {
                parts.add(searchId);
            } else {
                parts = Inventory.getAllParts();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part id not found!");
                alert.setTitle("Parts");
                alert.setHeaderText("Search Error");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException e) {
            parts = Inventory.lookUpPart(search);
            if (parts.size()==0){
                parts = Inventory.getAllParts();
                Alert alert = new Alert(Alert.AlertType.ERROR, "No results found from this search criteria!");
                alert.setTitle("Parts");
                alert.setHeaderText("Search Error");
                alert.showAndWait();
            }
        }
        partsTable.setItems(parts);
    }

    /**
     * This method opens the add parts screen if the add parts button is clicked.
     * @param actionEvent- Add part button is clicked.
     * @throws IOException
     */
    public void toAddParts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/c486/View/AddPartForm.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method opens the modify parts screen if the modify parts button is clicked, and handles sending the selected
     * part's data into the modify parts screen.<br> An error message is displayed if the user clicks the modify part
     * button without selecting a part to be modified first.<br>
     * @param actionEvent - Modify part button is clicked.
     * @throws IOException
     */
    public void toModifyParts(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c486/View/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartController modPartController = loader.getController();
            modPartController.getSelectedPart(partsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part from the table you want to modify.");
            alert.setTitle("Parts");
            alert.setHeaderText("Modify Error");
            alert.showAndWait();
        }
    }

    /**
     * This method opens the add product page if the add product button is clicked.
     * @param actionEvent - Add product button is clicked.
     * @throws IOException
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/c486/View/AddProduct.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1100, 800 );
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method opens the modify product page if the modify product button is clicked, and handles sending the selected
     * product's data into the modify product screen.<br> An error message is displayed if the user clicks the modify product
     * button without selecting a product to be modified first.<br>
     * @param actionEvent - Modify product button is clicked.
     * @throws IOException - NullPointerException if a product isn't selected first before modify product button is clicked.
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/c486/View/ModifyProduct.fxml"));
            loader.load();
            ModifyProductController modProductController = loader.getController();
            modProductController.getSelectedProduct(productsTable.getSelectionModel().getSelectedItem());

            Stage stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product from the table you want to modify.");
                alert.setTitle("Products");
                alert.setHeaderText("Modify Error");
                alert.showAndWait();
            }
        }

    /**
     * This method grabs the selected part's data and deletes it.<br> It will confirm with the user before deletion of part
     * as well as displaying an error message if no part is selected or if the part was not deleted.<br>
     * @param actionEvent - Delete part button is pressed.
     */
    public void onDeletePart(ActionEvent actionEvent) {
        /**
         * Variable of type Part that retrieves the data from the user selected part.
         */
        Part selected = partsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR, "No part selected, please select a part to be deleted.");
            nullAlert.setTitle("Parts");
            nullAlert.setHeaderText("Delete Error");
            nullAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            alert.setTitle("Parts");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (Inventory.deletePart(selected)) {
                    partsTable.setItems(Inventory.getAllParts());
                }
                else {
                    Alert nullAlert = new Alert(Alert.AlertType.WARNING, "Part was not deleted");
                    nullAlert.setTitle("Parts");
                    nullAlert.setHeaderText("Delete Warning");
                    nullAlert.showAndWait();
                }
            }
            else{
                Alert nullAlert = new Alert(Alert.AlertType.WARNING, "Deletion canceled, part was not deleted");
                nullAlert.setTitle("Parts");
                nullAlert.setHeaderText("Delete Warning");
                nullAlert.showAndWait();
            }
        }
    }

    /**
     * This method extracts the input from the search text field and calls the lookUpProduct method from the Inventory class
     * and searches through the array list for a match and returns results to the productTable.<br>
     * If no results are found an error message is displayed saying no results are found.<br>
     * @param actionEvent - Enter key is pressed when focus is on the search text field.
     */
    public void onProductSearch(ActionEvent actionEvent) {
        /**
         * String variable that retrieves the input from the product search text field.
         */
        String searchPr = productsSearchTxt.getText();

        /**
         * Array list of type Product that contains any string matches from the inputted search criteria.
         */
        ObservableList<Product>products = Inventory.lookUpProduct(searchPr);

            try{
                int productId = Integer.parseInt(searchPr);
                Product searchId = Inventory.lookUpProduct(productId);
                if(searchId != null) {
                    products.add(searchId);
                }
                else{
                    products = Inventory.getAllProducts();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Product id not found!");
                    alert.setTitle("Product Search");
                    alert.setHeaderText("Search Error");
                    alert.showAndWait();
                }
            }
            catch(NumberFormatException e){
                products = Inventory.lookUpProduct(searchPr);
                 if(products.size() == 0) {
                     products = Inventory.getAllProducts();
                     Alert alert = new Alert(Alert.AlertType.ERROR, "No results found from this search criteria!");
                     alert.setTitle("Product Search");
                     alert.setHeaderText("Search Error");
                     alert.showAndWait();
                 }
            }
        productsTable.setItems(products);
    }

    /**
     * This method grabs the selected product's data and deletes it.<br> It will confirm with the user before deleting the product
     * as well as displaying an error message if no product is selected or if the product was not deleted.<br>
     * @param actionEvent - Delete product button is clicked.
     */
    public void toDeleteProduct(ActionEvent actionEvent) {
        /**
         * Variable of type Product that retrieves the data from the product selected by the user.
         */
        Product selected = productsTable.getSelectionModel().getSelectedItem();

        if(selected == null){
            Alert nullAlert = new Alert(Alert.AlertType.ERROR, "No product selected, please select a product to be deleted.");
            nullAlert.setTitle("Products");
            nullAlert.setHeaderText("Delete Error");
            nullAlert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
            alert.setTitle("Products");
            alert.setHeaderText("Confirm Delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (selected.getAssociatedParts().isEmpty()) {
                    if(Inventory.deleteProduct(selected)) {
                        partsTable.setItems(Inventory.getAllParts());
                    }
                }
                else {
                   Alert partsAlert = new Alert(Alert.AlertType.ERROR, "Product contains associated parts. The associated parts must be removed before product deletion.");
                   partsAlert.setTitle("Products");
                   partsAlert.setHeaderText("Delete Error");
                   partsAlert.showAndWait();
                }
            }
            else {
                Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Product was not deleted");
                deleteAlert.setTitle("Products");
                deleteAlert.showAndWait();
            }
        }
    }

    /**
     * This method executes the program once the exit button is pressed or returns to main form if the no button is pressed.
     * @param event - Exit button is clicked.
     */
    public void exitProgram(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        alert.setTitle("Inventory Management System");
        alert.setHeaderText("Confirm Exit");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == buttonTypeYes) {
            System.exit(0);
        }
    }
 }




