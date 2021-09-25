module com.tyshkun.calculatorapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;


    opens com.tyshkun.calculatorapplication to javafx.fxml;
    exports com.tyshkun.calculatorapplication;
}