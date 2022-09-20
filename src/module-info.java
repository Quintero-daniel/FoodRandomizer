module foodRandomizer {
    requires java.xml;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.gluonhq.charm.glisten;

    opens sample to javafx.fxml; // Sample is the package name in this case
    opens sample.dataModel to javafx.fxml;

    exports sample;
    exports sample.dataModel;
}