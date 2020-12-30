public class Eqv extends Exp {


    public Eqv(Exp exp1, Exp exp2) {
	super("Eqv?",exp1,exp2);
    }


    public Exp getArg1() {
	return (Exp) getSubTree(0);
    }

    public Exp getArg2() {
	return (Exp) getSubTree(1);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitEqv(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ","  + getArg2().toString() + ")";
    }
}
