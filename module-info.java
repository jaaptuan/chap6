module se233.chapter6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.logging.log4j;


    opens se233.chapter6 to javafx.fxml;
    exports se233.chapter6;
}