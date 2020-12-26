public abstract class ExpLit<T> extends SMPLExp {

    protected T V;

    public ExpLit(T value) {
	super(value.toString());
	V = value;
    }

    public abstract T getVal();

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpLit(this, arg);
    }

    public String toString() {
	return V.toString();
    }
}

