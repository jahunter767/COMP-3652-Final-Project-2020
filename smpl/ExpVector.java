import java.util.ArrayList;

public class ExpVector extends Exp {


    public ExpVector(ArrayList<Exp> vect) {
	super("vector", vect);
    }

    public ArrayList<Exp> getVal(){
    return getSubTrees();
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpVector(this, arg);
    }

    public String toString() {
	return getName() + "[]";
    }
}
