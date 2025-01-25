package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 *<p>
 * This is the main class and where the program starts. It calls the loader and loads the main form screen
 * and populates the program with test data.<br> This application creates an Inventory Management System with a fully functional
 * GUI.<br> The user can create parts and products and store them in varying inventory lists.<br>
 * The user can modify and delete these parts and products as well.<br>
 * <p>
 * <b>FUTURE FEATURE:</b> <br>In a future update on the Inventory Management System, I will upgrade the inventory management
 * functionality to reflect the current stock of parts.<br> The program will have a working running total on the inventory
 * of a part.<br> This means that when a part is added into an associated parts list for a product, the stock of that part
 * will be decreased by the number of parts used for that product.<br> This upgrade will allow systems to be put in to place to
 * notify the end user once a part's inventory decreases below a minimum inventory level or right before so ample time is given to reorder
 * that part.<br>
 * <br>
 * <b> RUNTIME ERROR:</b><br> The location for this error description is in the AddPartController class and inside the
 * onAddSavePart method summary.<br>
 * <br>
 * <b>Javadocs:</b> The Javadoc files are inside the project folder in a folder named <i>javadocs</i>.
 *
 * @author Justin House
 */
public class MainApplication extends Application {

    /**
     * This is the start method that passes the primary stage and loads the main form screen. The start method is a
     * method from the base parent Application.
     * @param primaryStage - The stage that the loads first which is the main form screen.
     * @throws IOException - This happens when the fxml file cannot be found.
     */
    @Override
    public void start(Stage primaryStage)throws IOException  {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/c486/View/MainForm.fxml")));
        primaryStage.setScene(new Scene(root,1500, 600));
        primaryStage.show();
    }

    /**
     * This method adds test data into the main form's part table which is called in the main method.<br>
     * The method creates different objects of Outsourced parts, InHouse parts, and Products.<br> This method adds these
     * objects to the allParts and allProducts array lists.<br>
     */
    public static void addTestData(){
        Outsourced a = new Outsourced(25, "Brakes", 9.99, 9, 1, 9, "Husky");
        Outsourced b = new Outsourced(30, "Wheel", 21.45, 4, 1, 9, "GoodY");
        InHouse c = new InHouse(42, "Seat", 15.99, 8, 1, 9, 531);
        InHouse j = new InHouse(75, "Rope", 14.00, 8, 1, 9, 533);
        Product d = new Product(998, "Giant Bike", 299.99, 5, 2, 5);
        Product e = new Product(999, "Tricycle", 99.99, 3, 1, 8);

        Inventory.addProduct(d);
        Inventory.addProduct(e);
        Inventory.addPart(a);
        Inventory.addPart(b);
        Inventory.addPart(c);
        Inventory.addPart(j);

    }

    /**
     * This is the main method, and it is the first code that is called when the program starts.<br> It calls the addTestData
     * method to populate the test data in the parts and products table views and calls the launch method.<br>
     * @param args - Command line arguments.
     */
    public static void main(String[] args) {
        addTestData();
        launch();
    }
}