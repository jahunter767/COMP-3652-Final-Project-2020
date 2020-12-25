import java.util.ArrayList;


public class SMPLFunction extends SMPLObject<Closure> {
    public SMPLFunction(Closure val) {
	super(val);
    }

    public Closure getClosure(){
        return getVal();
    }

    public String toString(){
	return "Type: SMPLFunction\nValue: " + getVal().toString();
    }

}
