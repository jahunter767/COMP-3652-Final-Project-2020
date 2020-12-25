import java.util.ArrayList;


public class SMPLFunction extends SMPLObject<Closure> {

    
    public SMPLFunction(String type, Closure val) {
	super(type, val);
    }

    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal().toString();
    }


    public String printVal(){
	return getVal().toString();
    }


}
