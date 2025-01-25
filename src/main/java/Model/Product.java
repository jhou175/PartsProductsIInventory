package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This is the Product class and this class handles all the product's getters and setters.<br>This class also handles the
 * associated parts for all products.
 */
public class Product  {
    /**
     * This array list contains all current associated parts for each instance of a product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *The id of the product.
     */
    private int id;

    /**
     *The name of the product.
     */
    private String name;

    /**
     *The price of the product.
     */
    private double price;

    /**
     *The stock of the product.
     */
    private int stock;

    /**
     * The maximum stock of the product.
     */
    private int max;

    /**
     * The minimum stock of the product.
     */
    private int min;



    /**
     * The constructor method for products.<br> It initializes the id, name, price, stock, maximum, and minimum
     * for each product object.
     * @param id - The integer value for the id of the product.
     * @param name - The String name of the product.
     * @param price - The double value for the price of the product.
     * @param stock - The integer value for the stock of the product.
     * @param min - The integer value for the minimum stock of the product.
     * @param max - The integer value for the maximum stock of the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * @param stock the stock/inventory to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * @param min the minimum to set
     */
    public void setMin(int min) { this.min = min; }

    /**
     * @param max the maximum to set
     */
    public void setMax(int max) { this.max = max; }

    /**
     * @return the id
     */
    public int getId() { return id; }

    /**
     * @return the name
     */
    public String getName() { return name; }

    /**
     * @return the price
     */
    public double getPrice() { return price; }

    /**
     * @return the stock
     */
    public int getStock() { return stock; }

    /**
     * @return the min
     */
    public int getMin() { return min; }

    /**
     * @return the max
     */
    public int getMax() { return max; }



    /**
     * This method adds the associated part that is passed in through part into the associatedParts array list.
     * @param part the associated part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * This method removes the associated part from the associatedParts list and table.
     * @param selectedAssociatedPart - The associated port to be removed.
     * @return associatedParts array list with the results of removing selectedPart.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method retrieves and returns the associated parts array list
     * @return associatedParts
     */
    public ObservableList<Part> getAssociatedParts() { return associatedParts; }




}
