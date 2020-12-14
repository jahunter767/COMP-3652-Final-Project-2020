public class SmplDouble extends ExpLit {

    double val;

    public SmplDouble(Double v) {
	super(v.toString());
	val = v.doubleValue();
    }

    public double getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplDouble(this, arg);
    }

    public String toString() {
	return Double.toString(val);
    }
}
