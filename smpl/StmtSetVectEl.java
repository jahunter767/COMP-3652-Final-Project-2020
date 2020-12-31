public class StmtSetVectEl extends Statement {

    Exp vect;

    public StmtSetVectEl(Exp v, Exp index, Exp val) {
    super("vectorMutation", index, val);
    this.vect = v;
    }

    public Exp getVect(){
    return this.vect;
    }

    public Exp getIndex(){
    return (Exp) getSubTree(0);
    }

    public Exp getValue(){
    return (Exp) getSubTree(1);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitStmtSetVectEl(this, arg);
    }

    public String toString(){
    return "VectorMutation :" + getVect().toString();
    }

}

