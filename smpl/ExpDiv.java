public class ExpDiv extends ExpBinOp {

    public ExpDiv(SMPLExp e1, SMPLExp e2) {
	super("/", e1, e2);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpDiv(this, arg);
    }
}

