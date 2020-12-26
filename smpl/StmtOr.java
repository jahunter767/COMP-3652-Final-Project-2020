public class StmtOr extends ExpBinOp {

    public StmtOr(SMPLExp exp1, SMPLExp exp2){
	super("or", exp1, exp2);
    }
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitStmtOr(this, arg);
    }
}