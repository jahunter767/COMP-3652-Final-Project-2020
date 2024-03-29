public class ExpOr extends ExpBinOp {

    public ExpOr(Exp e1, Exp e2) {
	super("OR", e1, e2);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpOr(this, arg);
    }

}

