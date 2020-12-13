
/**
 * IR Class to represent a function definition new vers
 */
import java.util.ArrayList;
public class StmtConditionalElseStmt extends StmtConditionalStmt {
    Exp predicate;
    StmtSequence consequent;
    StmtSequence alternative;


    protected StmtConditionalElseStmt(Exp pred, StmtSequence cons, StmtSequence alt) {	// placeholder; can be removed eventually
	super("conditionalElse",pred,cons);
	this.predicate = pred;
	this.consequent = cons;
	this.alternative = alt;
    }


    public Exp getPredicate() {	
	return this.predicate;
    }


    public StmtSequence getConsequent() {	
	return this.consequent;
    }

    public StmtSequence getAlternative() {	
	return this.alternative;
    }

    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {	
	return v.visitStmtConditionalElseStmt(this,arg);
    }



}
