public class ExpLit extends Exp {

    String type;
    Object val;

    public ExpLit(String t, Object v) {
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

    public Object getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpLit(this, arg);
    }

    public String toString() {
	return type;
    }
}

