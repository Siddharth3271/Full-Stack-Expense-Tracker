module org.example.expensetrackerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires com.google.gson;
    requires java.desktop;


    opens org.example.expensetrackerclient to javafx.fxml;
    exports org.example.expensetrackerclient;
}