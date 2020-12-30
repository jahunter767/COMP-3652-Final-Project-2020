import java.util.ArrayList;

public class ExpVector extends ExpBinOp {

    ArrayList<Exp> vals;	


    public ExpVector(ArrayList<Exp> vect) {
	super("vector", vect);
	this.vals = vect;
    }

    public ArrayList<Exp> getVal(){
    return this.vals;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpVector(this, arg);
    }

    public String toString() {
	return getName() + "(" + getExpL().toString() + ","  + getExpR().toString() +  ")";
    }
}