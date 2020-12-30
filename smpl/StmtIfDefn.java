import java.util.*;
/**
 * IR Class to represent a function definition
 */
public class StmtIfDefn extends Statement {
    // Implement this class
	private SMPLExp predicate;
	private StmtSequence consequent;
	private Statement alternative;
	
	
    public StmtIfDefn(SMPLExp p,StmtSequence c, Statement a ) {	// placeholder; can be removed eventually
	super("IfStmt");
	predicate = p;
	consequent = c;
	alternative = a;
    }
	
	public StmtIfDefn(SMPLExp p,StmtSequence c) {	// placeholder; can be removed eventually
	super("IfStmt");
	predicate = p;
	consequent = c;
    }
	
	public SMPLExp getPredicate(){
	return predicate;
	}
	
	public StmtSequence getConsequent(){
	return consequent;
	}
	
	public Statement getAlternative(){
	return alternative;
	}
	
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtIfDefn(this, arg);
    }
	
}
