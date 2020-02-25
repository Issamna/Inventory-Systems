/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package View_Controller;

import Model.Inventory;
import Model.*;
import static Model.Inventory.*;
import static View_Controller.MainScreen.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ModifyProduct implements Initializable {
    @FXML
    public Label idField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField maxField;
    @FXML
    public TextField minField;
    @FXML
    public TextField stockField;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<Part> availablePartsTable;
    @FXML
    public TableView<Part> selectedPartsTable;
    @FXML
    public TableColumn<Part, Integer> selectedIDCol;
    @FXML
    public TableColumn<Part, String> selectedNameCol;
    @FXML
    public TableColumn<Part, Integer> selectedStockCol;
    @FXML
    public TableColumn<Part, String> selectedPriceCol;
    @FXML
    public TableColumn<Part, Integer> availableIDCol;
    @FXML
    public TableColumn<Part, String> availableNameCol;
    @FXML
    public TableColumn<Part, Integer> availableStockCol;
    @FXML
    public TableColumn<Part, String> availablePriceCol;
    private int modifyIndex;
    private ObservableList<Part> partToSave = FXCollections.observableArrayList();
    private ObservableList<Part> partToFill = FXCollections.observableArrayList();
    private Stage stage;
    private Parent scene;

    @FXML //search parts form available table
    public void onActionSearch(ActionEvent event) {
        if (isInteger(searchField.getText())) {
            if(Inventory.lookupPart(Integer.parseInt(searchField.getText())) != null) {
                ObservableList<Part> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupPart(Integer.parseInt(searchField.getText())));
                availablePartsTable.setItems(tempList);
            }
            else {
                informationDialog("Search Error", "Not found", "The search term is not in the inventory.");
            }
        }
        else {
            if(Inventory.lookupPart(searchField.getText()) != null) {
                ObservableList<Part> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupPart(searchField.getText()));
                availablePartsTable.setItems(tempList);
            }
            else {
                informationDialog("Search Error", "Not found", "The search term is not in the inventory.");
            }
        }
    }

    @FXML //add part to selected
    public void onActionAddPart(ActionEvent event) {
        Part temp = availablePartsTable.getSelectionModel().getSelectedItem();
        if(temp != null) {
            partToSave.add(temp);
            updateSelectedPartsTable();
        }
        else{
            informationDialog("Add Error", "Selection Error", "Nothing selected to add");
        }
    }

    @FXML //delete from selected list
    public void onActionDeletePart(ActionEvent event) {
        try {
            Part partDel = selectedPartsTable.getSelectionModel().getSelectedItem();
            String content = "Are you sure you want to delete " + partDel.getName().get() + "?";
            if (confirmDialog("Delete?", content)) {
                partToSave.remove(partDel);
                updateSelectedPartsTable();
            }
        }
        catch (Exception e){
            informationDialog("Delete Error", "Selection Error", "Nothing selected to delete");
        }
    }

    @FXML //save modified product
    public void onActionSave(ActionEvent event) {
        try{
            int partStock = Integer.parseInt(stockField.getText());
            int partMin = Integer.parseInt(minField.getText());
            int partMax = Integer.parseInt(maxField.getText());
            if(partMax < partMin) {
                informationDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(partStock < partMin || partStock> partMax) {
                informationDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else {
                IntegerProperty id = new SimpleIntegerProperty(Integer.parseInt(idField.getText()));
                StringProperty name = new SimpleStringProperty(nameField.getText());
                IntegerProperty stock = new SimpleIntegerProperty(partStock);
                DoubleProperty price = new SimpleDoubleProperty(Double.parseDouble(priceField.getText()));
                IntegerProperty min = new SimpleIntegerProperty(partMin);
                IntegerProperty max = new SimpleIntegerProperty(partMax);
                Product temp = new Product(id, name, price, stock, min, max);
                for (Part part : partToSave) {
                    temp.addAssociatedPart(part);
                }
                Inventory.updateProduct(modifyIndex, temp);
                resetProdModifyIndex();
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e){
            informationDialog("Input Error", "Error in adding part", "Check fields for correct input");
        }
    }

    @FXML //cancel
    public void onActionCancel(ActionEvent event)throws IOException {
        if(confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML //reset search/table
    public void onActionReset(ActionEvent event) {
        searchField.setText("");
        updatePartsTable();
    }

    //update selected table
    private void updateSelectedPartsTable(){
        selectedPartsTable.setItems(partToSave);
    }

    //update available table
    private void updatePartsTable(){
        availablePartsTable.setItems(getAllParts());
    }

    private boolean isInteger(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product modifyProduct = Inventory.lookupProduct(getProdModifyIndex());
        modifyIndex = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(getProdModifyIndex()));
        idField.setText(modifyProduct.getId().getValue().toString());
        nameField.setText(modifyProduct.getName().get());
        stockField.setText(modifyProduct.getStock().getValue().toString());
        priceField.setText(modifyProduct.getPrice().getValue().toString());
        maxField.setText(modifyProduct.getMax().getValue().toString());
        minField.setText(modifyProduct.getMin().getValue().toString());
        partToFill = modifyProduct.getAllAssociatedParts();
        idField.setText(modifyProduct.getId().getValue().toString());

        selectedIDCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        selectedNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        selectedStockCol.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());
        selectedPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceString());
        availableIDCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        availableNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        availableStockCol.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());
        availablePriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceString());
        updatePartsTable();
        selectedPartsTable.setItems(partToFill);
    }
}
