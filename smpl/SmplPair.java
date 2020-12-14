import java.util.ArrayList;

public class SmplPair extends ExpLit {

    ArrayList<ExpLit> pair;

    public SmplPair(ExpLit v1, ExpLit v2) {
	super("Pair");
    pair = new ArrayList<ExpLit>();
    pair.add(v1);
    pair.add(v2);
    }

    public SmplPair(ArrayList<ExpLit> p) {
	super("Pair");
    pair = p;
    }

    public ArrayList<ExpLit> getVal() {
	return pair;
    }

    public ExpLit getLeft() {
	return pair.get(0);
    }

    public ExpLit getRight() {
	return pair.get(1);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplPair(this, arg);
    }

    public String toString() {
	return "(" + getLeft().toString() + ", " + getRight().toString() + ")";
    }
}
