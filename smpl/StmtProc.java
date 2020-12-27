import java.util.*;
/**
 * IR Class to represent an unamed procedure
 */
public class StmtProc extends Statement {
    // Implement this class
	private StmtSequence sseq;
	private ArrayList<SMPLExp> exps;
	private ArrayList<SMPLExp> params;
	private Boolean flag = false;
	
    public StmtProc(ArrayList<SMPLExp> xps, StmtSequence ss, ArrayList<SMPLExp> args) {	// placeholder; can be removed eventually
	super("ProcStmt");
	sseq = ss;
	exps = xps;
	params = new ArrayList<SMPLExp>();
	if(args.size() > 0){
		flag = true; //Lambda expression found
		params = args;
	}
    }
	public Boolean isLambda(){
	return flag; 
	}

	public ArrayList<SMPLExp> getArgs(){
	return params; 
	}
	public StmtSequence getStmt(){
	return sseq;
	}
	
	public ArrayList<SMPLExp> getExps(){
	return exps;
	}
	
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtProc(this, arg);
    }
	
}
