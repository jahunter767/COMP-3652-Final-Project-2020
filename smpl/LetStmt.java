import java.util.*;
/**
 * IR Class to represent a function definition
 */
public class LetStmt extends Statement {
    // Implement this class
	private ArrayList<Pair> bindings;
	private StmtSequence body;
	
	
    public LetStmt(StmtSequence body, ArrayList<Pair> bindings) {	// placeholder; can be removed eventually
	super("LetStmt");
	this.bindings = bindings;
	this.body = body;
    }
	
	public ArrayList<Pair> getBindings(){
	return bindings;
	}
	
	public StmtSequence getBody(){
	return body;
	}
	
	
	public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitLetStmt(this, arg);
    }
	
}
