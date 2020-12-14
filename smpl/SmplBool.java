public class SmplBool extends ExpLit {

    boolean val;

    public SmplBool(Boolean v) {
	super(v.toString());
	val = v.booleanValue();
    }

    public boolean getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplBool(this, arg);
    }

    public String toString() {
	return new String(val);
    }
}
