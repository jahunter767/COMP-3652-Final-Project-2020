public class SmplString extends ExpLit {

    String val;

    public SmplString(String v) {
	super(v);
	val = v;
    }

    public String getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplString(this, arg);
    }

    public String toString() {
	return val;
    }
}
