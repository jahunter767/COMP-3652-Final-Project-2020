import java.util.*;
/**
 * IR Class to represent a named procedure
 */
public class StmtProcDefn extends Statement {
    // Implement this class
	private StmtSequence sseq;
	private ArrayList<SMPLExp> exps;
	private ArrayList<SMPLExp> params;
	private Boolean flag;
	private String name;
	
    public StmtProcDefn(String procName, ArrayList<SMPLExp> xps, StmtSequence ss, ArrayList<SMPLExp> args) {	// placeholder; can be removed eventually
	super("funDef");
	sseq = ss;
	exps = xps;
	name = procName;
	params = new ArrayList<SMPLExp>();
	if(args.size() > 0){
		flag = true ; //Lamba expression found
		params = args;
	}
    }
	
	public String getName(){
	return name;
	}
	
	public Boolean getFlag(){
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
	return v.visitStmtProcDefn(this, arg);
    }
	
}
