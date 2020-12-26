public class StmtNot extends SMPLExp{
	
	public StmtNot(SMPLExp e1) {
	super("not", e1);
    }
	
	public SMPLExp getExpL() {
	return (SMPLExp) getSubTree(0);
    }
	
    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitStmtNot(this, arg);
    }
	
	public String toString() {
	return getExpL().toString() + getName();
    }
}