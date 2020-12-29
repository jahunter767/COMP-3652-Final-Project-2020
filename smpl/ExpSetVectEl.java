public class ExpSetVectEl extends Exp {

    String id;

    public ExpSetVectEl(String id, Exp index, Exp val) {
    super("vectorAccess", index, val);
    this.id = id;
    }

    public String getId(){
    return id;
    }

    public Exp getIndex(){
    return (Exp) getSubTree(0);
    }

    public Exp getValue(){
    return (Exp) getSubTree(1);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpSetVectEl(this, arg);
    }

}

