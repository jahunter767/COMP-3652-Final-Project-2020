import java.util.*;
/**
 * IR Class to represent a function definition
 */
public class StmtCaseDefn extends Statement {
    // Implement this class
	private ArrayList<Pair> clauses;
	
    public StmtCaseDefn(ArrayList<Pair> clauses) {	// placeholder; can be removed eventually
	super("CaseStmt");
	this.clauses = clauses;
    }
	
	public ArrayList<Pair> getClauses(){
	return clauses;
	}
	
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtCaseDefn(this, arg);
    }
	
}