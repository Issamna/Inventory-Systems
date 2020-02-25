/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package View_Controller;

import static View_Controller.MainScreen.*;
import Model.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPart implements Initializable {

    @FXML
    public RadioButton inHouse;
    @FXML
    public RadioButton outsourced;
    @FXML
    public TextField nameField;
    @FXML
    public TextField stockField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField maxField;
    @FXML
    public TextField minField;
    @FXML
    public TextField typeNameField;
    @FXML
    public Label typeNameLabel;
    @FXML
    public Label idFiled;
    private boolean isInHouse = true;
    private int id;
    private Stage stage;
    private Parent scene;

    @FXML //radio toggle
    public void onActionInhouse(ActionEvent event) {
        typeNameLabel.setText("Machine ID");
        outsourced.setSelected(false);
        isInHouse = true;
    }

    @FXML //radio toggle
    public void onActionOutsourced(ActionEvent event) {
        typeNameLabel.setText("Company Name");
        inHouse.setSelected(false);
        isInHouse = false;
    }

    @FXML //save part
    public void onActionSave(ActionEvent event) {
        try {
            int partStock = Integer.parseInt(stockField.getText());
            int partMin = Integer.parseInt(minField.getText());
            int partMax = Integer.parseInt(maxField.getText());
            if(partMax < partMin) {
                informationDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(partStock < partMin || partStock> partMax) {
                informationDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else{
                IntegerProperty id = new SimpleIntegerProperty(this.id);
                StringProperty name = new SimpleStringProperty(nameField.getText());
                IntegerProperty stock = new SimpleIntegerProperty(partStock);
                DoubleProperty price = new SimpleDoubleProperty(Double.parseDouble(priceField.getText()));
                IntegerProperty min = new SimpleIntegerProperty(partMin);
                IntegerProperty max = new SimpleIntegerProperty(partMax);
                if (isInHouse) {
                    IntegerProperty machineID = new SimpleIntegerProperty(Integer.parseInt(typeNameField.getText()));
                    InHousePart temp = new InHousePart(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(temp);
                } else {
                    StringProperty companyName = new SimpleStringProperty(typeNameField.getText());
                    OutSourcedPart temp = new OutSourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(temp);
                }
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e){
            informationDialog("Input Error", "Error in adding part", "Check fields for correct input" );
        }
    }

    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        if(confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id = Inventory.getPartCount();
        idFiled.setText("Auto-Gen: " + id);
    }
}
