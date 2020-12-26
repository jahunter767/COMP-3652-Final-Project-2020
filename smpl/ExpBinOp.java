/**
 * Class to represent an expression that is a binary operator.
 */
public abstract class ExpBinOp extends SMPLExp {

    protected ExpBinOp(String name, SMPLExp exp1, SMPLExp exp2){
	super(name, exp1, exp2);
    }

    public SMPLExp getExpL() {
	return (SMPLExp) getSubTree(0);
    }

    public SMPLExp getExpR() {
	return (SMPLExp) getSubTree(1);
    }

    public String toString() {
	return getExpL().toString() + getName() + getExpR().toString();
    }

}
