
/**
 * IR Class to represent a function definition new vers
 */
import java.util.ArrayList;

public class ExpConditional extends Exp {
    Exp predicate;
    StmtSequence consequent;
    StmtSequence alternative;
    // Implement this class


    protected ExpConditional(Exp pred, StmtSequence cons) {	// placeholder; can be removed eventually
	super("conditional",cons);
	this.predicate = pred;
	this.consequent = cons;
	this.alternative = null;
    }

    protected ExpConditional(String name,Exp pred, StmtSequence cons) {	// placeholder; can be removed eventually
	super(name,cons);
	this.predicate = pred;
	this.consequent = cons;
	this.alternative = null;
    }



    protected ExpConditional(Exp pred, StmtSequence cons, StmtSequence alt) {	// placeholder; can be removed eventually
	super("conditional",cons, alt);
	this.predicate = pred;
	this.consequent = cons;
	this.alternative = alt;
    }

    protected ExpConditional(String name,Exp pred, StmtSequence cons, StmtSequence alt) {	// placeholder; can be removed eventually
	super(name,cons,alt);
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

    public Exp getExp() {
	return getSubTree(0);
    }

    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {	
	return v.visitExpConditional(this,arg);
    }

    public String toString() {
	return getExp().toString();
    }


}
