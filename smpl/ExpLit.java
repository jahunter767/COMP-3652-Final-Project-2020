public abstract class ExpLit<T> extends SMPLExp {

    protected T V;

    public ExpLit(T value) {
	super(value.toString());
	V = value;
    }

    public abstract T getVal();

    public String toString() {
	return V.toString();
    }
	
	public T getLit(){
		return this.V;
	}
	
	public void setLit(T lit){
		this.V = lit;
	}
	
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpLit(this, arg);
    }
	
}

