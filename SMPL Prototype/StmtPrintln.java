public class StmtPrintln extends Statement {

    public StmtPrintln(Exp str) {
    super("println", str);
    }

    public Exp getExp() {
	return getSubTree(0);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtPrintln(this, arg);
    }

    public String toString() {
	return "println(" +  getExp().toString() + ")";
    }
}