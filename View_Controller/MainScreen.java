/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package View_Controller;

import Model.*;
import static Model.Inventory.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable{

    @FXML
    public TableView<Part> partsTable;
    @FXML
    public TableColumn<Part, Integer> partIDCol;
    @FXML
    public TableColumn<Part, String> partNameCol;
    @FXML
    public TableColumn<Part, Integer> partStockCol;
    @FXML
    public TableColumn<Part, String> partPriceCol;
    @FXML
    public TableView<Product> prodTable;
    @FXML
    public TableColumn<Product, Integer> prodIDCol;
    @FXML
    public TableColumn<Product, String> prodNameCol;
    @FXML
    public TableColumn<Product, Integer> prodStockCol;
    @FXML
    public TableColumn<Product, String> prodPriceCol;
    @FXML
    public TextField partSearchField;
    @FXML
    public TextField prodSearchField;
    private Stage stage;
    private Parent scene;
    private static int partModifyID = -1;
    private static int prodModifyID = -1;


    @FXML //part search
    public void onActionPartSearch(ActionEvent event) {
            if (isInteger(partSearchField.getText())) {
                if(Inventory.lookupPart(Integer.parseInt(partSearchField.getText())) != null) {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(Integer.parseInt(partSearchField.getText())));
                    partsTable.setItems(tempList);
                }
                else {
                    informationDialog("Search Error", "Not found", "The search term is not in the inventory.");
                }
            }
            else {
                if(Inventory.lookupPart(partSearchField.getText()) != null) {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(partSearchField.getText()));
                    partsTable.setItems(tempList);
                }
                else {
                    informationDialog("Search Error", "Not found", "The search term is not in the inventory.");
                }
            }
    }

    @FXML //product search
    public void onActionProdSearch(ActionEvent event) {
        if (isInteger(prodSearchField.getText())) {
            if(Inventory.lookupProduct(Integer.parseInt(prodSearchField.getText())) != null) {
                ObservableList<Product> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupProduct(Integer.parseInt(prodSearchField.getText())));
                prodTable.setItems(tempList);
            }
            else {
                informationDialog("Search Error", "Not found", "The search term is not in the inventory.");
            }
        }
        else {
            if(Inventory.lookupProduct(prodSearchField.getText()) != null) {
                ObservableList<Product> tempList = FXCollections.observableArrayList();
                tempList.add(Inventory.lookupProduct(prodSearchField.getText()));
                prodTable.setItems(tempList);
            }
            else {
                informationDialog("Search Error", "Not found", "The search term is not in the inventory.");
            }
        }
    }

    @FXML //add part
    public void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddPart.fxml"));
        stage.setTitle("Inventory Management System - Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML //modify part
    public void onActionModifyPart(ActionEvent event) {
        try {
            partModifyID = partsTable.getSelectionModel().getSelectedItem().getId().getValue();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            stage.setTitle("Inventory Management System - Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e){
            informationDialog("Modify Error", "Selection Error", "Nothing selected to modify");
        }
    }

    @FXML //delete part
    public void onActionDelPart(ActionEvent event) {
        try {
            Part partDel = partsTable.getSelectionModel().getSelectedItem();
            String content = "Are you sure you want to delete " + partDel.getName().get() + "?";
            if (confirmDialog("Delete?", content)) {
                deletePart(partDel);
                partTableReset();
            }
        }
        catch (Exception e){
            informationDialog("Delete Error", "Selection Error", "Nothing selected to delete");
        }
    }

    @FXML //add product
    public void onActionAddProd(ActionEvent event)throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        stage.setTitle("Inventory Management System - Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML //modify product
    public void onActionModifyProd(ActionEvent event) {
        try {
            prodModifyID = prodTable.getSelectionModel().getSelectedItem().getId().getValue();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
            stage.setTitle("Inventory Management System - Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e){
            informationDialog("Modify Error", "Selection Error", "Nothing selected to modify");
        }
    }

    @FXML //delete product
    public void onActionDelProduct(ActionEvent event) {
        try {
            Product prodDel = prodTable.getSelectionModel().getSelectedItem();
            String content = "Are you sure you want to delete " + prodDel.getName().get() + "?";
            if (confirmDialog("Delete?", content)) {
                deleteProduct(prodDel);
                partTableReset();
            }
        }
        catch (Exception e){
            informationDialog("Delete Error", "Selection Error", "Nothing selected to delete");
        }
    }

    @FXML //exit
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML //reset search/table
    public void onActionPartReset(ActionEvent event) {
        partTableReset();
    }

    @FXML //reset search/table
    public void onActionProdReset(ActionEvent event) {
        prodTableReset();
    }

    //adjust selection for modify
    static int getPartModifyIndex(){
        return partModifyID;
    }
    static void resetPartModifyIndex(){
        partModifyID = -1;
    }
    static int getProdModifyIndex(){
        return prodModifyID;
    }
    static void resetProdModifyIndex(){
        prodModifyID = -1;
    }

    //fill parts table
    private void partsTableFill(){
        partsTable.setItems(getAllParts());
    }

    //fill product table
    private void productsTableFill(){
        prodTable.setItems(getAllProducts());
    }

    //check if integer for search
    private boolean isInteger(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    //reset part table
    private void partTableReset(){
        partSearchField.setText("");
        partsTableFill();
    }

    //reset product table
    private void prodTableReset(){
        prodSearchField.setText("");
        productsTableFill();
    }

    //confirm dialog
    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    //information dialog
    static void informationDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        partNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        partStockCol.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());
        partPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceString());
        prodIDCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        prodNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        prodStockCol.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());
        prodPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceString());
        partsTableFill();
        productsTableFill();
    }
}
