/**
 * Class to represent an expression that is a binary comparator.
 */
public abstract class ExpBinComp extends Exp {

    protected ExpBinComp(String name, Exp exp1, Exp exp2){
	super(name, exp1, exp2);
    }

    public Exp getLeftPred() {
	return (Exp) getSubTree(0);
    }

    public Exp getRightPred() {
	return (Exp) getSubTree(1);
    }

    public String toString() {
	return getLeftPred().toString() + getName() + getRightPred().toString();
    }

}
