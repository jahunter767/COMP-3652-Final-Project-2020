
public class StmtPrint extends Statement {

    public StmtPrint(Exp str) {
    super("print", str);
    }

    public Exp getExp() {
	return getSubTree(0);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtPrint(this, arg);
    }

    public String toString() {
	return "print(" +  getExp().toString() + ")";
    }
}