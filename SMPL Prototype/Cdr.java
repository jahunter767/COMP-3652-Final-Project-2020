public class Cdr extends Exp {


    public Cdr(Exp exp1) {
	super("cdr",exp1);
    }


    public Exp getArg1() {
	return (Exp) getSubTree(0);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitCdr(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ")";
    }
}
