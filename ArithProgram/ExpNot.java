public class ExpNot extends Exp {

    public ExpNot(Exp e) {
	super("Not", e);
    }

    public Exp getExp() {
	return getSubTree(0);
    }



    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpNot(this, arg);
    } 


    public String toString() {
	return getName() + getExp().toString();
    }

}