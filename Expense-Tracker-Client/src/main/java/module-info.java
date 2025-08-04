module org.example.expensetrackerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires com.google.gson;
    requires java.desktop;

    //this is crucial to be able to read data from models and store them into our tables
    opens org.example.expensetrackerclient.Models to javafx.base;

    opens org.example.expensetrackerclient to javafx.fxml;
    exports org.example.expensetrackerclient;
}