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
	
	public String visitExpCallStmt(ExpCallStmt fd, Void arg)
	throws VisitException {
	return "(define ";
    }
	
	public String visitStmtProc(StmtProc fd, Void arg)
	throws VisitException {
	return "(define "+"( "+fd.getStmt().visit(this,arg)+" )";
    }

    public String visitStmtProcDefn(StmtProcDefn fd, Void arg)
	throws VisitException {
	// Convert parameters to String
	StringBuffer sb = new StringBuffer();
	for (Object v: fd.getExps()){
	sb.append(v.toString());
	sb.append(" ");
	}
	String str = sb.toString();
	return "(define "+"( "+fd.getName()+" "+str+")"+fd.getStmt().visit(this,arg)+" )";
    }

    public String visitExpProcCall(ExpProcCall fc, Void arg)
	throws VisitException {
	// to be implemented
	// Convert parameters to String
	StringBuffer sb = new StringBuffer();
	for (Object v: fc.getExp()){
	sb.append(v.toString());
	sb.append(" ");
	}
	String str = sb.toString();
	return "(define "+"( "+fc.getVar()+" "+str+")"+" )";
    }

    // expressions
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
	throws VisitException {
	return exp.toString();
    }

    public String visitExpVar(ExpVar exp, Void arg)
	throws VisitException {
	return exp.getVar();
    }
	public String visitCompareGE(CompareGE exp, Void arg) 
	throws VisitException {
		return "Greater than OR equal to";
	}
	public String visitCompareG(CompareG exp, Void arg) 
	throws VisitException {
		return "Greater than";
	}
	public String visitCompareE(CompareE exp, Void arg) 
	throws VisitException {
		return "Greater than";
	}
	public String visitCompareNE(CompareNE exp, Void arg) 
	throws VisitException {
		return "Greater than";
	}
	public String visitCompareLE(CompareLE exp, Void arg) 
	throws VisitException {
		return "Less than";
	}
	public String visitCompareL(CompareL exp, Void arg) 
	throws VisitException {
		return "Less than Or equal to";
	}
	
	public String visitStmtNot(StmtNot exp, Void arg) 
	throws VisitException {
		return "NOT";
	}
	
	public String visitStmtAnd(StmtAnd exp, Void arg) 
	throws VisitException {
		return "AND";
	}
	
	public String visitStmtOr(StmtOr exp, Void arg) 
	throws VisitException {
		return "Or";
	}
	
	public String visitStmtIfDefn(StmtIfDefn exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
	public String visitStmtCaseDefn(StmtCaseDefn exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
	public String visitSubVector(SubVector exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
	public String visitTupleOperations(TupleOperations exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	public String visitPrintLn(PrintLn exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
	public String visitEqualityOperations(EqualityOperations exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
	public String visitLetStmt(LetStmt exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
	public String visitSliceOperations(SliceOperations exp, Void arg) 
	throws VisitException {
		return "IF-STATEMENT";
	}
	
}
