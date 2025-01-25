module c486.c486assessmentproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens Model to javafx.fxml;
    exports Model;
    exports Controller;

    opens Controller to javafx.fxml;


}