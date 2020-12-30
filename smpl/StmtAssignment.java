public class StmtAssignment extends Statement {

    String var;

    public StmtAssignment(String id, Exp e) {
	super(":=", e);
	var = id;
    }

    public String getVar(){
	return var;
    }

    public Exp getExp() {
	return getSubTree(0);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStmtAssignment(this, arg);
    }

    public String toString() {
	return String.format("%s := %s", getVar(), getExp().toString());
    }
}