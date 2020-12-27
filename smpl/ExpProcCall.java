import java.util.*;
/**
 * IR Class to represent a function call
 */
public class ExpProcCall extends SMPLExp {
    
    // Implement this class
	private String var;
	private ArrayList<SMPLExp> exp;

    public ExpProcCall(String v, ArrayList<SMPLExp> xps) {	// placeholder; can be removed eventually
	super("call");
	var = v;
	exp = xps;
    }
	
	public String getVar(){
	return var;
	}
	
	public ArrayList<SMPLExp> getExp(){
	return exp;
	}
	
	public String toString() {
	return String.format("%s := %s", getVar(), getExp().toString());
    }
	
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpProcCall(this, arg);
    }

}
