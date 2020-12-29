public class Size extends Exp {

    String id;

    public Size(String id) {
    super("size");
    this.id = id;
    }

    public String getArg1() {
	return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSize(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ")";
    }
}
