public class ExpNot extends Exp {

    public ExpNot(Exp e1) {
	super("NOT", e1);
    }

    public Exp getPredicate(){
        return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpNot(this, arg);
    }

    public String toString(){
        return getName() + getPredicate().toString();
    }

}

