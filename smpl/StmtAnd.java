public class StmtAnd extends ExpBinOp {

    public StmtAnd(SMPLExp exp1, SMPLExp exp2){
	super("and", exp1, exp2);
    }
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitStmtAnd(this, arg);
    }
}