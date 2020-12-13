import java.util.*;

public class ToScheme implements Visitor<Void, String> {

    String result;

    public ToScheme() {
	result = "";
    }

    public String getResult() {
	return result;
    }

    public Void getDefaultState() {
	return null;
    }

    // program
    public String visitArithProgram(ArithProgram p, Void arg)
	throws VisitException {
	result = (String) p.getSeq().visit(this, arg);
	return result;
    }

    // statements
    public String visitStatement(Statement stmt, Void arg)
	throws VisitException {
	return stmt.getExp().visit(this, arg);
    }

    public String visitStmtSequence(StmtSequence exp, Void arg)
	throws VisitException {
	ArrayList stmts = exp.getSeq();
	if (stmts.size() == 1)
	    return ((Statement) stmts.get(0)).visit(this,
						    arg);
	else {
	    Iterator iter = stmts.iterator();
	    String result = "(begin ";
	    Statement stmt;
	    while (iter.hasNext()) {
		stmt = (Statement) iter.next();
		result += (String) stmt.visit(this, arg) +
		    "\n";
	    }
	    result += ")";
	    return result;
	}
    }

    public String visitStmtDefinition(StmtDefinition sd, Void arg)
	throws VisitException {
	String valExp = (String) sd.getExp().visit(this,
						   arg);
	return "(define " + sd.getVar() + " " +
	    valExp + ")";
    }

    public String visitStmtFunDefn(StmtFunDefn fd, Void env)
	throws VisitException {
	// to be implemented
	return "";
    }

    public String visitStmtConditionalStmt(StmtConditionalStmt cs, Void env)
	throws VisitException {
	// to be implemented
	return "";
    }

    public String visitStmtConditionalElseStmt(StmtConditionalElseStmt cs, Void env)
	throws VisitException {
	// to be implemented
	return "";
    }

    public String visitExpFunCall(ExpFunCall fc, Void env)
	throws VisitException {
	// to be implemented
	return "";
    }

    // expressions

    public String visitExpConditional(ExpConditional cs, Void env)
	throws VisitException {
	// to be implemented
	return "";
    }


    public String visitExpAdd(ExpAdd exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(+ " + left + " " + right + ")";
    }
    public String visitExpSub(ExpSub exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(- " + left + " " + right + ")";
    }

    public String visitExpMul(ExpMul exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(* " + left + " " + right + ")";
    }

    public String visitExpDiv(ExpDiv exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(/ " + left + " " + right + ")";
    }

    public String visitExpMod(ExpMod exp, Void arg)
	throws VisitException{
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(mod " + left + " " + right + ")";
    }

    public String visitExpLit(ExpLit exp, Void arg)
	throws VisitException{
	return "" + exp.getVal();
    }

    public String visitExpVar(ExpVar exp, Void arg)
	throws VisitException {
	return exp.getVar();
    }

    public String visitExpLessThan(ExpLessThan exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(< " + left + " " + right + ")";
    }






    public String visitExpLessThanEqual(ExpLessThanEqual exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(<= " + left + " " + right + ")";
    }

    public String visitExpGreaterThan(ExpGreaterThan exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(> " + left + " " + right + ")";
    }

    public String visitExpGreaterThanEqual(ExpGreaterThanEqual exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(>= " + left + " " + right + ")";
    }

    public String visitExpEqual(ExpEqual exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(= " + left + " " + right + ")";
    }

    public String visitExpNotEqual(ExpNotEqual exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(!= " + left + " " + right + ")";
    }




    public String visitExpNot(ExpNot exp, Void arg)
	throws VisitException {
	String value = exp.getExp().visit(this, arg);
	return "(not " + value + ")";
    }

    public String visitExpAnd(ExpAnd exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(and " + left + " " + right + ")";
    }


    public String visitExpOr(ExpOr exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(or " + left + " " + right + ")";
    }

    public String visitExpComparator(ExpComparator exp, Void arg)
	throws VisitException {
	return "";
    }

}
