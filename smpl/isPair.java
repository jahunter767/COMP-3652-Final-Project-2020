public class isPair extends Exp {


    public isPair(Exp exp1) {
	super("pair?",exp1);
    }


    public Exp getArg1() {
	return (Exp) getSubTree(0);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitisPair(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ")";
    }
}