/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partCount = 0;
    private static int prodCount = 0;

    //Return part count for part id
    public static int getPartCount(){
        return partCount++;
    }

    //Return product count for product id
    public static int getProdCount(){
        return prodCount++;
    }

    //add part to list
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    //add product to list
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    //look up part by int return part or null
    public static Part lookupPart(int partId){
        Part temp = null;
        for (Part part : allParts) {
            if (partId == part.getId().getValue()) {
                temp = part;
            }
        }
        return temp;
    }
    //look up product by int return part or null
    public static Product lookupProduct(int productId){
        Product temp = null;
        for (Product product : allProducts) {
            if (productId == product.getId().getValue()) {
                temp = product;
            }
        }
        return temp;
    }
    //look up part by string return part or null
    public static Part lookupPart(String partName){
        Part temp = null;
        for (Part part : allParts) {
            if (partName.equals(part.getName().get())) {
                temp = part;
            }
        }
        return temp;
    }
    //look up product by string return part or null
    public static Product lookupProduct(String productName){
        Product temp = null;
        for (Product product : allProducts) {
            if (productName.equals(product.getName().get())) {
                temp = product;
            }
        }
        return temp;
    }

    //update part
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    //update product
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    //delete part
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    //delete product
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
    //return all parts in list
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    //return all products in list
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


}
