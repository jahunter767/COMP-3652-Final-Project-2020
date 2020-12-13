public class ExpLessThanEqual extends ExpBinOp {

    public ExpLessThanEqual(Exp e1, Exp e2) {
	super("<=", e1, e2);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpLessThanEqual(this, arg);
    } 

}