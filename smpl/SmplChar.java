public class SmplChar extends ExpLit {

    char val;

    public SmplChar(Character v) {
	super(v.toString());
	val = v.charValue();
    }

    public char getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplChar(this, arg);
    }

    public String toString() {
	return new String(val);
    }
}
