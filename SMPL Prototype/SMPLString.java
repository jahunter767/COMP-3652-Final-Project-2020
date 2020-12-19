import java.util.ArrayList;


public class SMPLString extends SMPLObject<String> {

    
    public SMPLString(String type, String val) {
	super(type, val);

    }


    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal();
    }


    public String printVal(){
	return String.valueOf(getVal());
    }




}
