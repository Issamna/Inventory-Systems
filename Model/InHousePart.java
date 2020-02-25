/*
C482 Performance Assessment
Issam Ahmed
000846138
2/11/2020
*/
package Model;

import javafx.beans.property.*;

public class InHousePart extends Part {

    private IntegerProperty machineId;

    //constructor
    public InHousePart(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock,IntegerProperty min, IntegerProperty max, IntegerProperty machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    //Mutator (setters)
    public void setMachineId(IntegerProperty machineId){
        this.machineId = machineId;
    }

    //Accessors (getters)
    public IntegerProperty getMachineId(){
        return machineId;
    }

}
