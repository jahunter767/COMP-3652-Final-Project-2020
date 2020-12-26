import java.util.ArrayList;

public class Statement extends SMPLExp {

    //Exp exp;

    // public Statement() {
    // 	super();
    // }
    
    protected Statement(String name, SMPLExp... operands) {
	super(name, operands);
    }

    protected Statement(String name, ArrayList<SMPLExp> operandList) {
	super(name, operandList);
    }

    public Statement(SMPLExp e) {
	//exp = e;
	super("stmt", e);
    }

    public SMPLExp getExp() {
	return getSubTree(0);
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStatement(this, arg);
    }

    public String toString() {
	return getExp().toString();
    }
}
