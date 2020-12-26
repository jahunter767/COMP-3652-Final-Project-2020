public class ExpRead extends Exp {

    public ExpRead() {
	super("read");
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpRead(this, arg);
    }

    public String toString(){
        return getName() + "()";
    }

}

