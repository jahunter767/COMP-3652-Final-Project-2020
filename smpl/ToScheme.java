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


    public SMPLObject visitSubstr(Substr exp, Void arg)
	throws VisitException {
	String arg1, arg2, arg3;
	arg1 = exp.getArg1().toString() + " ";
	arg2 = exp.getArg2().toString() + " ";
	arg3 = exp.getArg3().toString();
	return "(substr" + arg1 + + arg2 + arg3 ")";
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

    // expressions
	public String visitStmtFunDefn(StmtFunDefn fd, Void arg)
	throws VisitException {
	ArrayList<String> params = fd.getParameters();
	String result = "(proc (params " + params.remove(0);
	for (String p : params){
		result = result + " " + p;
	}
	String ovf = fd.getParamOvf();
	if (ovf != null){
		result = result + " (rest " + ovf + ")";
	}
	result = result + ") (body " + fd.getBody().toString() + ")";
	return result;
    }

    public String visitExpFunCall(ExpFunCall fc, Void arg)
	throws VisitException {
	ArrayList<Exp> args = fc.getArgs();
	String result = "(call " + fc.getName() + " (args " + args.remove(0).toString();
	for (Exp a : args){
		result = result + " " + a.toString();
	}
	result = result + ") )";
	return result;
	}

	public String visitExpCall(ExpCall fc, Void arg)
	throws VisitException {
	Exp args = fc.getArgs();
	Exp body = fc.getFunction();
	String result = "(call " + body.visit(this, arg) + "(args " + args.visit(this, arg) + ") )";
	return result;
	}


	public String visitExpIf(ExpIf ifStmt, Void arg)
	throws VisitException {
	String result = "(if " + ifStmt.getPredicate() + 
					" (consequence " + ifStmt.getConsequent() +
					") (alternative " + ifStmt.getAlternative() + ")";
	return result;
    }

	public String visitExpCase(ExpCase c, Void arg)
	throws VisitException {
	ArrayList<ExpClauses> clauses = c.getClauses();
	String result = "(case " + clauses.remove(0).toString();
	for (Exp c : clauses){
		result = result + " " + c.toString();
	}
	result = result + " )"
	return result;
    }


	public String visitExpBitwiseAnd(ExpBitwiseAnd exp, Void arg)
	throws VisitException{
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(& " + left + " " + right + ")";
	}

	public String visitExpBitwiseOr(ExpBitwiseOr exp, Void arg)
	throws VisitException{
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(| " + left + " " + right + ")";
    }

	public String visitExpBitwiseNot(ExpBitwiseNot exp, Void arg)
	throws VisitException{
	String pred = exp.getPredicate().visit(this, arg);
	return "(~ " + pred + ")";
	}


	public String visitExpLess(ExpLess exp, Void arg)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpLess r = new ExpLess(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, arg);
	}else{
		String left1 = exp.getLeftPred().visit(this, arg);
		String right1 = exp.getRightPred().visit(this, arg);
		return "(< " + left1 + " " + right1 + ")";
	}
	}

	public String visitExpLessEq(ExpLessEq exp, Void arg)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpLessEq r = new ExpLessEq(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, arg);
	}else{
		String left1 = exp.getLeftPred().visit(this, arg);
		String right1 = exp.getRightPred().visit(this, arg);
		return "(<= " + left1 + " " + right1 + ")";
	}
	}

	public String visitExpEqual(ExpEqual exp, Void arg)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpEqual r = new ExpEqual(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, arg);
	}else{
		String left1 = exp.getLeftPred().visit(this, arg);
		String right1 = exp.getRightPred().visit(this, arg);
		return "(== " + left1 + " " + right1 + ")";
	}
	}

	public String visitExpGreaterEq(ExpGreaterEq exp, Void arg)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpGreaterEq r = new ExpGreaterEq(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, arg);
	}else{
		String left1 = exp.getLeftPred().visit(this, arg);
		String right1 = exp.getRightPred().visit(this, arg);
		return "(>= " + left1 + " " + right1 + ")";
	}
	}

	public String visitExpGreater(ExpGreater exp, Void arg)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpGreater r = new ExpGreater(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, arg);
	}else{
		String left1 = exp.getLeftPred().visit(this, arg);
		String right1 = exp.getRightPred().visit(this, arg);
		return "(> " + left1 + " " + right1 + ")";
	}
	}

	public String visitExpNotEqual(ExpNotEqual exp, Void arg)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpNotEqual r = new ExpNotEqual(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, arg);
	}else{
		String left1 = exp.getLeftPred().visit(this, arg);
		String right1 = exp.getRightPred().visit(this, arg);
		return "(!= " + left1 + " " + right1 + ")";
	}
	}


	public String visitExpAnd(ExpAnd exp, Void arg)
	throws VisitException{
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(and " + left + " " + right + ")";
	}

	public String visitExpOr(ExpOr exp, Void arg)
	throws VisitException{
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(or " + left + " " + right + ")";
    }

	public String visitExpNot(ExpNot exp, Void arg)
	throws VisitException{
	String pred = exp.getPredicate().visit(this, arg);
	return "(not " + pred + ")";
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
	return "(% " + left + " " + right + ")";
    }

	public String visitExpPow(ExpPow exp, Void arg)
	throws VisitException{
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(^ " + left + " " + right + ")";
    }


    public String visitExpVar(ExpVar exp, Void arg)
	throws VisitException {
	return exp.getVar();

	public String visitExpLit(ExpLit exp, Void arg)
	throws VisitException {
	return exp.toString();
    }

}
