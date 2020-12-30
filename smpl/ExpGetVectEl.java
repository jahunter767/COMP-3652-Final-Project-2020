public class ExpGetVectEl extends Exp {

    Exp vect;

    public ExpGetVectEl(Exp v, Exp index) {
    super("vectorAccess", index);
    this.vect = v;
    }

    public Exp getVect(){
    return this.vect;
    }

    public Exp getIndex(){
    return (Exp) getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpGetVectEl(this, arg);
    }

    public String toString(){
    return "VectorAccess :" + getVect().toString();
    }
}

