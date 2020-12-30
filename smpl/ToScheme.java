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


    public String visitSubstr(Substr exp, Void arg)
	throws VisitException {
	String arg1, arg2, arg3;
	arg1 = exp.getArg1().toString() + " ";
	arg2 = exp.getArg2().toString() + " ";
	arg3 = exp.getArg3().toString();
	return "(substr" + arg1 + arg2 + arg3 + ")";
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
	String valExp = sd.getExp().visit(this, arg);
	return "(define " + sd.getVar() + " " + valExp + ")";
    }

	public String visitStmtAssignment(StmtAssignment sa, Void arg)
	throws VisitException {
	String valExp = sa.getExp().visit(this, arg);
	return "(:= " + sa.getVar() + " " + valExp + ")";
    }


	public String visitStmtPrint(StmtPrint p, Void arg)
	throws VisitException{
	return "(print " + p.getExp().visit(this, arg) + ")";
	}

	public String visitStmtPrintln(StmtPrintln p, Void arg)
	throws VisitException{
	return "(print " + p.getExp().visit(this, arg) + ")";
	}


    // expressions
	public String visitStmtFunDefn(StmtFunDefn fd, Void arg)
	throws VisitException {
	ArrayList<String> params = fd.getParams();
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
	String result = "(call " + fc.getFunction().visit(this, arg) +
			" (args " + args.remove(0).toString();
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
	String result = "(call " + body.visit(this, arg) +
			"(args " + args.visit(this, arg) + ") )";
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
	ArrayList<Exp> clauses = c.getClauses();
	String result = "(case " + clauses.remove(0).visit(this, arg);
	for (Exp cl : clauses){
		result = result + " " + cl.visit(this, arg);
	}
	result = result + " )";
	return result;
    }

	public String visitExpClause(ExpClause c, Void arg)
	throws VisitException {
	String result = "(clause " + c.getPredicate().visit(this, arg) + 
					" (consequence " + c.getConsequent().visit(this, arg) + ")";
	return result;
    }


	public String visitExpLet(ExpLet l, Void arg)
	throws VisitException{
	ArrayList<ExpBind> bindLst = l.getBindings();
	String result = "(let (bindings " + bindLst.remove(0).visit(this, arg);
	for (ExpBind b: bindLst){
		result = result + " " + b.visit(this, arg);
	}
	result = result + ") (body " + l.getBody().visit(this, arg) + ")";
	return result;
	}

	public String visitExpBind(ExpBind b, Void arg)
	throws VisitException{
	String result = "(bind " + b.getName() + " ";
	result = result + b.getExpr().visit(this, arg) + ")";
	return result;
	}


	public String visitExpRead(ExpRead r, Void arg)
	throws VisitException{
	return "(read)";
	}

	public String visitExpReadInt(ExpReadInt r, Void arg)
	throws VisitException{
	return "(readInt)";
	}


    public String visitExpPair(ExpPair exp, Void arg)
	throws VisitException {
	String obj1, obj2;
	obj1 = exp.getExpL().visit(this, arg) + " ";
	obj2 = exp.getExpR().visit(this, arg);
	return "(pair " + obj1 + obj2 + ")";
    }

    public String visitCar(Car exp, Void arg)
	throws VisitException {
	String arg1;
	arg1 = exp.getArg1().visit(this, arg);
	return "(car " + arg1 + ")";
    }

    public String visitCdr(Cdr exp, Void arg)
	throws VisitException {
	String arg1;
	arg1 = exp.getArg1().visit(this, arg);
	return "(cdr " + arg1 + ")";
    }

    public String visitisPair(isPair exp, Void arg)
	throws VisitException {
	String arg1;
	arg1 = exp.getArg1().visit(this, arg);
	return "(pair? " + arg1 + ")";
    }


    public String visitExpList(ExpList lst, Void arg)
	throws VisitException{
	String result = "(list ";
	ArrayList<Exp> exp = lst.getArgs(); // expressions that we got as arguments
	result = result + exp.remove(0).visit(this, arg);
	for(Exp e: exp){
		result = result + " " + e.visit(this, arg);
	}
	result = result + ")";
	return result;
    }


    public String visitExpVector(ExpVector vect, Void arg)
	throws VisitException{
	String args = "(vector ";
	ArrayList<Exp> exp = vect.getVal();	
	result = result + exp.remove(0).visit(this, arg);
	for(Exp e: exp){
		result = result + " " + e.visit(this, arg);
	}
	result = result + ")";
	return result;
    }

	public String visitSize(Size exp, Void arg)
	throws VisitException{
	String arg1 = exp.getArg1().visit(this, arg);
	return "(size " + arg1 + ")";
    }

	public String visitExpGetVectEl(ExpGetVectEl exp, Void arg)
	throws VisitException{
	String vect, index;
	vect = exp.getVect().visit(this, arg) + " ";
	index = exp.getIndex().visit(this, arg);
	return "(vectAccess " + vect + index + ")";
    }

	public String visitExpSetVectEl(ExpSetVectEl exp, Void arg)
	throws VisitException{
	String vect, index, val;
	vect = exp.getVect().visit(this, arg) + " ";
	index = exp.getIndex().visit(this, arg) + " ";
	val = exp.getValue().visit(this, arg);
	return "(vectMutation " + vect + index + val + ")";
    }


	public String visitEqual(Equal exp, Void arg)
	throws VisitException{
	String a1 = exp.getArg1().visit(this, arg) + " ";
	String a2 = exp.getArg2().visit(this, arg);
	return "(eqeal?" + a1 + a2 + ")";
    }

	public String visitEqv(Eqv exp, Void arg)
	throws VisitException{
	String a1 = exp.getArg1().visit(this, arg) + " ";
	String a2 = exp.getArg2().visit(this, arg);
	return "(eqeal?" + a1 + a2 + ")";
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
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(< " + left + " " + right + ")";
	}

	public String visitExpLessEq(ExpLessEq exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(<= " + left + " " + right + ")";
	}

	public String visitExpEqual(ExpEqual exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(== " + left + " " + right + ")";
	}

	public String visitExpGreaterEq(ExpGreaterEq exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(>= " + left + " " + right + ")";
	}

	public String visitExpGreater(ExpGreater exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(> " + left + " " + right + ")";
	}

	public String visitExpNotEqual(ExpNotEqual exp, Void arg)
	throws VisitException {
	String left = exp.getExpL().visit(this, arg);
	String right = exp.getExpR().visit(this, arg);
	return "(!= " + left + " " + right + ")";
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
	}

	public String visitExpLit(ExpLit exp, Void arg)
	throws VisitException {
	return exp.toString();
    }

}
