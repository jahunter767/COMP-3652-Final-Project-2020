public class ExpSub extends ExpBinOp {

    public ExpSub(SMPLExp e1, SMPLExp e2) {
	super("-", e1, e2);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpSub(this, arg);
    }
}

