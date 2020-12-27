import java.util.*;
/**
 * IR Class to represent an unamed procedure
 */
public class ExpCallStmt extends Statement {
	
	SMPLExp proc;
	SMPLExp list;
	
    public ExpCallStmt(SMPLExp proc,SMPLExp list) {	// placeholder; can be removed eventually
	super("CallStmt");
	this.proc = proc;
	this.list = list;
    }
	
	public SMPLExp getList(){
		return list;
	}
	public SMPLExp getProc(){
		return proc;
	}
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpCallStmt(this, arg);
    }
	
}
