public class CompareG extends RelationOP {
	
	public CompareG(SMPLExp e1, SMPLExp e2) {
	super(">", e1, e2);
	}
	
    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitCompareG(this, arg);
    }

}