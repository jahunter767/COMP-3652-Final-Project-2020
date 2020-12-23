public class ExpLit<T> extends Exp {

    private T V;

    public ExpLit(T value) {
	super(value.toString());
	V = value;
    }

    public T getVal() {
	return V;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpLit(this, arg);
    }

    public String toString() {
	return V.toString();
    }
}

