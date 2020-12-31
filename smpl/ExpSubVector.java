public class ExpSubVector extends Exp {

    private Exp count;

    public ExpSubVector(Exp c, Exp proc) {
	super("subVector", proc);
	this.count = c;
    }

    public Exp getCount(){
	return this.count;
    }

    public Exp getFunction(){
	return (Exp) getSubTree(0);
    }

	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpSubVector(this, arg);
    }	
	public String toString(){
	return getCount().toString() + ": " + getFunction().toString();
    }
}

