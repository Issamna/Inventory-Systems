/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package Model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

public abstract class Part {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty stock;
    private IntegerProperty min;
    private IntegerProperty max;

    //constructor
    public Part(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock,IntegerProperty min, IntegerProperty max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Mutator (setters)
    public void setId(IntegerProperty id){
        this.id = id;
    }

    public void setName(StringProperty name){
        this.name = name;
    }

    public void setPrice(DoubleProperty price){
        this.price = price;
    }

    public void setStock(IntegerProperty stock){
        this.stock = stock;
    }

    public void setMin(IntegerProperty min){
        this.min = min;
    }

    public void setMax(IntegerProperty max){
        this.max = max;
    }

    //Accessors (getters)
    public IntegerProperty getId(){
        return id;
    }

    public StringProperty getName(){
        return name;
    }

    public DoubleProperty getPrice(){
        return price;
    }

    //getter to get price as string format for table column
    public StringProperty getPriceString(){
        String priceFormat = Bindings.format("%.2f", price).get();
        return new SimpleStringProperty("$"+ priceFormat);
    }

    public IntegerProperty getStock(){
        return stock;
    }

    public IntegerProperty getMin(){
        return min;
    }

    public IntegerProperty getMax(){
        return max;
    }
}
