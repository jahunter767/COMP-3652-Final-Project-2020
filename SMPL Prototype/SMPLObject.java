import java.util.ArrayList;
import java.util.Arrays;


public abstract class SMPLObject<T> {

    private String name;    // the type name of this node (e.g. "+", "if")
    private String type;    // the type of this node (e.g. "numbers", "string")
    private T val;    // the value that is wrapped in each subclass


    public SMPLObject(String type, T val) {
	this.type = type;
	this.val = val;

    }

    public SMPLObject() {
	this.type = null;
	this.val = null;
    }



    public T getVal() {
	return val;
    }


    public void setVal( T newVal) {
	this.val = newVal;
    }


    public String getType() {
	return type;
    }


    public boolean isEqualType(String otherType) {
	return type.equals(otherType);
    }


    public abstract String toString();

    public String printVal(){
		return "";
	} // use to print the content of the object


    public SMPLObject add(SMPLObject obj) throws TypeException {
	throw new TypeException();
    }


    protected SMPLObject sub(SMPLObject obj) throws TypeException {
	throw new TypeException();
    }





}
