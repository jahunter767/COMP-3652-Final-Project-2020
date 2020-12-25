public class ExpBitwiseNot extends Exp {

    public ExpBitwiseNot(Exp e1) {
	super("~", e1);
    }

    public Exp getPredicate(){
        return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpBitwiseNot(this, arg);
    }

    public String toString(){
        return getName() + getPredicate().toString();
    }

}

