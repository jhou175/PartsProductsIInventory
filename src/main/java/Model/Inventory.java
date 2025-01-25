package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Inventory class and this class holds both the parts and products array lists.<br> This class implements the methods
 * to add and remove parts and products, search for parts and products, update parts and products, and methods to retrieve the
 * parts and products array lists.<br>
 *
 */
public class Inventory  {

    /**
     * The array list that contains all current parts available in the Inventory Management System.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     *The array list that contains all current products available in the Inventory Management System.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds the part passed in into the allParts array list.
     * @param newPart - The new part to be added to the parts list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds the new product passed in into the allProducts array list.
     * @param newProduct - The new product to be added to the products list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method searches through the allParts array list using the inputted string that has been parsed to an integer
     * and returns the results or null if none are found.
     * @param partId - The number entered in by the user to be searched for.
     * @return - Returns either the part that matches the partId entered or null if it is not found.
     */
    public static Part lookUpPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part Id : allParts) {
            if (Id.getId() == partId) {
                return Id;
            }
        }
        return null;
    }

    /**
     * This method searches through the allProducts array list using the inputted string that has been parsed to an integer
     * and returns the results or null if none are found.
     * @param productId - The number entered in by the user to be searched for.
     * @return - Returns either the product that matches the partId entered or null if it is not found.
     */
    public static Product lookUpProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product Id : allProducts) {
            if (Id.getId() == productId) {
                return Id;
            }
        }
        return null;
    }

    /**
     * This method creates a partNameSearch array list and searches through the allParts array list using the inputted
     * string and returns any matches.
     * @param Name - The string entered in by the user to be searched for.
     * @return - Returns the part that matches the string name that was entered.
     */
    public static ObservableList<Part> lookUpPart(String Name) {

        /**
         * This array list of Part contains any part matches found that match the passed String name.
         */
        ObservableList<Part> partNameSearch = FXCollections.observableArrayList();

        /**
         * This array list is populated with all the parts inside the allParts list and is used in the enhanced for loop
         * to search for the part name that is passed into the lookUpPart method.
         */
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part P : allParts) {
            if (P.getName().contains(Name)) {
                partNameSearch.add(P);
            }
        }
        return partNameSearch;
    }

    /**
     * This method creates a productNameSearch array list and searches through the allProducts array list using the inputted
     * string and returns any matches.
     * @param Name - The string entered in by the user to be searched for.
     * @return - Returns the product that matches the string name that was entered.
     */
    public static ObservableList<Product> lookUpProduct(String Name) {
        /**
         * This array list of Product contains any part matches found that match the passed String name.
         */
        ObservableList<Product> productNameSearch = FXCollections.observableArrayList();

        /**
         * This array list is populated with all the products inside the allProducts list and is used in the enhanced for loop
         * to search for the product's name that is passed into the lookUpProducts method.
         */
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product Pr : allProducts) {
            if (Pr.getName().contains(Name)) {
                productNameSearch.add(Pr);
            }
        }
        return productNameSearch;
    }

    /**
     * This method replaces the selected part to be modified with the newly created object that contains
     * the updated part information.
     * @param index - index of the selected part in the allParts array list.
     * @param selectedPart - The part that is selected to be modified.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method replaces the selected product to be modified with the newly created object that contains
     * the updated product information.
     * @param index - The index of the selected product in the allProduct array list.
     * @param newProduct - The product that is selected to be modified.
     */
    public static void updateProduct(int index, Product newProduct) { allProducts.set(index,newProduct); }

    /**
     * This method is used to delete the part selected by the user from the allParts list.
     * @param selectedPart - The part selected by the user to be deleted.
     * @return - Returns the allParts arraylist with the results of deleting the part.
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * This method is used to delete the product selected by the user from the allProducts list.
     * @param selectedProduct - The product selected by the user to be deleted.
     * @return - Returns the allProducts array list with the results of deleting the product.
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);

    }

    /**
     * This method retrieves and returns all parts inside the allParts list.
     * @return - Data entries inside the parts list.
     */
    public static ObservableList <Part> getAllParts(){ return allParts; }

    /**
     * This method retrieves and returns all products inside the allProducts list.
     * @return - Product entries inside the allProducts array list.
     */
    public static ObservableList <Product> getAllProducts(){ return allProducts; }

}

