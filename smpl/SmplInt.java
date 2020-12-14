public class SmplInt extends ExpLit {

    int val;

    public SmplInt(Integer v) {
	super(v.toString());
	val = v.intValue();
    }

    public int getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplInt(this, arg);
    }

    public String toString() {
	return Integer.toString(val);
    }
}
