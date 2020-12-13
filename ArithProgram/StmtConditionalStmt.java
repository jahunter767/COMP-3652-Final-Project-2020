
/**
 * IR Class to represent a function definition new vers
 */
import java.util.ArrayList;
public class StmtConditionalStmt extends Statement {
    Exp predicate;
    StmtSequence consequent;
    // Implement this class


    protected StmtConditionalStmt() {	// placeholder; can be removed eventually
	super("conditionalStmt");
    }


    protected StmtConditionalStmt(Exp pred, StmtSequence cons) {	// placeholder; can be removed eventually
	super("conditional",cons);
	this.predicate = pred;
	this.consequent = cons;
    }

    protected StmtConditionalStmt(String name,Exp pred, StmtSequence cons) {	// placeholder; can be removed eventually
	super(name,cons);
	this.predicate = pred;
	this.consequent = cons;
    }

    public Exp getPredicate() {	
	return this.predicate;
    }


    public StmtSequence getConsequent() {	
	return this.consequent;
    }

    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {	
	return v.visitStmtConditionalStmt(this,arg);
    }



}
