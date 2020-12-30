public class ExpGetVectEl extends Exp {

    String id;

    public ExpGetVectEl(String id, Exp index) {
    super("vectorAccess", index);
    this.id = id;
    }

    public String getId(){
    return id;
    }

    public Exp getIndex(){
    return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpGetVectEl(this, arg);
    }

    public String toString() {
	return getName() + "[" + getIndex().toString() +  "]";
    }
}