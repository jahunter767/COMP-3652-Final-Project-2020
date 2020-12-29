public class ExpAnd extends ExpBinOp {

    public ExpAnd(Exp e1, Exp e2) {
	super("and", e1, e2);
    }

    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpAnd(this, arg);
    }

}