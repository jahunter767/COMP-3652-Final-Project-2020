public class SMPLNone extends SMPLObject<String> {

    public SMPLNone() {
	super("None","");
    }

    public String toString(){
	return "" + getType();//"Type: " + getType() + "\nValue: " + getVal();
    }

}