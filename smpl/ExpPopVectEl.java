public class ExpPopVectEl extends Exp {

    String id;

    public ExpPopVectEl(String id, Exp index) {
    super("vector Pop", index);
    this.id = id;
    }

    public String getId(){
    return id;
    }

    public Exp getIndex(){
    return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpPopVectEl(this, arg);
    }

    public String toString() {
	return getName() + "[" + getIndex().toString() +  "]";
    }
}