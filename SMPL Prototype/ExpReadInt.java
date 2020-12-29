public class ExpReadInt extends Exp {

    public ExpReadInt() {
	super("readInt");
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpReadInt(this, arg);
    }

    public String toString(){
        return getName() + "()";
    }

}