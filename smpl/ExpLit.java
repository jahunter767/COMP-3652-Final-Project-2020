public class ExpLit<D> extends Exp {

    String type;
    D val;

    public ExpLit(String t, D v) {
    super("literal");
    this.type = t;
	this.val = v;
    }

    public ExpLit(String t) {
    super("literal");
    this.type = t;
	this.val = null;
    }

    public String getType() {
	return type;
    }

    public D getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpLit(this, arg);
    }

    public String toString() {
	return type;
    }
}

