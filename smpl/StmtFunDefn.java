import java.util.*;
/**
 * IR Class to represent a function definition
 */
public class StmtFunDefn extends Statement {
    // Implement this class
	private String var;
	private StmtSequence sseq;
	private ArrayList<Object> exps;
	
    public StmtFunDefn(String v, ArrayList<Object> xps, StmtSequence ss ) {	// placeholder; can be removed eventually
	super("funDef");
	var = v;
	sseq = ss;
	exps = xps;
    }
	
	public String getFname(){
	return var;
	}
	
	public StmtSequence getStmt(){
	return sseq;
	}
	
	public ArrayList<Object> getExps(){
	return exps;
	}
	
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtFunDefn(this, arg);
    }
	
}
