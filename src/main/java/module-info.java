module org.example.expensetrackerclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.expensetrackerclient to javafx.fxml;
    exports org.example.expensetrackerclient;
}