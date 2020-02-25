/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package Model;

import javafx.beans.property.*;

public class OutSourcedPart extends Part {

    private StringProperty companyName;

    //constructor
    public OutSourcedPart(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock,IntegerProperty min, IntegerProperty max, StringProperty companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    //Mutator (setters)
    public void setCompanyName(StringProperty companyName){
        this.companyName = companyName;
    }

    //Accessors (getters)
    public StringProperty getCompanyName(){
        return companyName;
    }

}
