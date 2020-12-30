public class Size extends Exp {

    public Size(Exp e) {
    super("size", e);
    }

    public Exp getArg1() {
	return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSize(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ")";
    }
}
