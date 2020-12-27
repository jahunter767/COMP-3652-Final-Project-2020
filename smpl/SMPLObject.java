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

    public boolean isType(String Type, String otherType) {
	return Type.equals(otherType);
    }


    public abstract String toString();

    public String printVal(){
		return "";
	} // use to print the content of the object


    public SMPLObject add(SMPLObject obj) throws TypeException {
	throw new TypeException();
    }


    public SMPLObject sub(SMPLObject obj) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject Substr(SMPLObject arg1, SMPLObject arg2) throws TypeException, SubstringException{
	throw new TypeException();
    }

    public SMPLObject Eqv(SMPLObject obj) {
	Boolean result = false;
	if (this == obj) result = true;

	return SMPL.makeInstance(result);

    }


    public SMPLObject Equal(SMPLObject obj) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject car() throws TypeException {
	throw new TypeException();
    }

    public SMPLObject cdr() throws TypeException {
	throw new TypeException();
    }



}
