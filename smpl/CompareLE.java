public class CompareLE extends RelationOP {
	
	public CompareLE(SMPLExp e1, SMPLExp e2) {
	super("<=", e1, e2);
    }

	

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitCompareLE(this, arg);
    }

}