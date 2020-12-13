import java.util.*;

public class Evaluator implements Visitor<Environment<Double>, Double> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. -- new vers -- */

    // allocate state here
    protected Double result;	// result of evaluation
    private Double defaultValue;
    private Class<Double> myClass;
    private Double True;
    private Double False;

    protected Evaluator() {
	this(Double.NaN);
    }

    public Evaluator(Double defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = Double.class;
	result = defaultValue;
	True = 0D;
	False = -10D;
    }

    public Environment<Double> getDefaultState() {
	return Environment.makeGlobalEnv(myClass);
    }

    public Double visitArithProgram(ArithProgram p, Environment<Double> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
    }

    public Double visitStatement(Statement s, Environment<Double> env)
    throws VisitException {
	return s.getExp().visit(this, env);
    }

    public Double visitStmtSequence(StmtSequence sseq, Environment<Double> env)
	throws VisitException {
	// remember that env is the environment
	Statement s;
	ArrayList seq = sseq.getSeq();
	Iterator iter = seq.iterator();
	Double result = defaultValue;
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, env);
	}
	// return last value evaluated
	return result;
    }

    public Double visitStmtDefinition(StmtDefinition sd,
				      Environment<Double> env)
	throws VisitException {
	Double result;
	result = sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return result;
    }

    public Double visitStmtFunDefn(StmtFunDefn fd, Environment<Double> env)
	throws VisitException {
	Closure closure = new Closure(fd,env);
	double f = env.putClosure(fd.getName(),closure);
	// to be implemented
	return f;
    }

    public Double visitStmtConditionalStmt(StmtConditionalStmt cs, Environment<Double> env)
	throws VisitException {
	Exp pred = cs.getPredicate();
	double result = pred.visit(this,env);
	if (result != True) return False;
	
	return cs.getConsequent().visit(this,env);
    }

    public Double visitStmtConditionalElseStmt(StmtConditionalElseStmt cs, Environment<Double> env)
	throws VisitException {
	Exp pred = cs.getPredicate();
	Exp alt = cs.getAlternative();
	double result = pred.visit(this,env);
	if (result != True) return alt.visit(this,env);
	
	return cs.getConsequent().visit(this,env);
    }


    public Double visitExpConditional(ExpConditional cs, Environment<Double> env)
	throws VisitException {
	Exp pred = cs.getPredicate();
	Exp alt = cs.getAlternative();
	double result = pred.visit(this,env);
	if (alt != null && result != True) return alt.visit(this,env);
	
	if (result != True) return False;

	return cs.getConsequent().visit(this,env);
    }


    public Double visitExpFunCall(ExpFunCall fc, Environment<Double> env)
	throws VisitException, MismatchedParamsException {
	Closure Func = env.getClosure(fc.getName());
	ArrayList<Double> args = new ArrayList<>();
	ArrayList<Exp> exp = fc.getArgs();
	StmtFunDefn myFunc = Func.getFunction();
	ArrayList<String> parameters = myFunc.getParams();

	/*if (parameters.size() != exp.size()){
		throw new MismatchedParamsException(parameters.size(),exp.size());
	}*/
	
	for(int i = 0; i < parameters.size(); i++){
		args.add(exp.get(i).visit(this,env));
		
	}
	
	Environment newEnv = new Environment(Func.getClosingEnv(),parameters,args);
	
	return myFunc.getBody().visit(this,newEnv);
    }


    public Double visitExpAdd(ExpAdd exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 + val2;
    }

    public Double visitExpSub(ExpSub exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 - val2;
    }

    public Double visitExpMul(ExpMul exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 * val2;
    }

    public Double visitExpDiv(ExpDiv exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 / val2;
    }

    public Double visitExpMod(ExpMod exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 % val2;
    }

    public Double visitExpLit(ExpLit exp, Environment<Double> env)
	throws VisitException {
	return new Double(exp.getVal());
    }

    public Double visitExpVar(ExpVar exp, Environment<Double> env)
	throws VisitException {
	return env.get(exp.getVar());
    }

    public Double visitExpLessThan(ExpLessThan exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2, val3;
	
	// base case
	if (exp.getExpL().getSubTrees().size() == 0 || (exp.getExpL().getSubTree(0).getSubTrees().size() == 0 &&!(exp.getExpL().getName().equals("<")))){
		val1 = exp.getExpL().visit(this, env);
		val2 = exp.getExpR().visit(this, env);
		if(val1 < val2) return True;				//Double.valueOf(exp.getExpL().getSubTrees().size());
		return False;						//Double.valueOf(exp.getExpR().getSubTrees().size());
	}
									//return -101D;
	Exp LchildRchild = exp.getExpL().getSubTree(1); //x < y < z check if x < y if true
	Exp Rchild = exp.getExpR();
	
	val1 = exp.getExpL().visit(this, env);
	if (val1 != True) return False;
	
	val2 = LchildRchild.visit(this,env);
	val3 = Rchild.visit(this,env);
	if (val2 < val3) return True;
	return False;

	/*

	int val = (int)Math.round(val1);
	Exp r = new ExpLessThan(LchildRchild,Rchild);
	double valr = r.visit(this,env);
	return valr;//Rchild.visit(this,env);

	/*
									//return new Double(val);


	ExpAnd result = new ExpAnd(new ExpLit(val), new ExpLessThan (LchildRchild,Rchild));
	return result.visit(this,env); */
	/*
	Exp Rchild = exp.getExpR();
	if (Lchild.getSubTrees().size() <= 2) {
		return Rchild.visit(this,env); }//Double.valueOf(Lchild.getSubTrees().size());} */

    }



    public Double visitExpLessThanEqual(ExpLessThanEqual exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2, val3;
	
	// base case
	if (exp.getExpL().getSubTrees().size() == 0 || (exp.getExpL().getSubTree(0).getSubTrees().size() == 0 && !(exp.getExpL().getName().equals("<=")))){
		val1 = exp.getExpL().visit(this, env);
		val2 = exp.getExpR().visit(this, env);
		if(val1 <= val2) return True;				
		return False;
	}
									
	Exp LchildRchild = exp.getExpL().getSubTree(1); 
	Exp Rchild = exp.getExpR();
	
	val1 = exp.getExpL().visit(this, env);
	if (val1 != True) return False;
	
	val2 = LchildRchild.visit(this,env);
	val3 = Rchild.visit(this,env);
	if (val2 <= val3) return True;
	return False;

    }




    public Double visitExpGreaterThan(ExpGreaterThan exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2, val3;
	
	// base case
	if (exp.getExpL().getSubTrees().size() == 0 || (exp.getExpL().getSubTree(0).getSubTrees().size() == 0 && !(exp.getExpL().getName().equals(">")))){
		val1 = exp.getExpL().visit(this, env);
		val2 = exp.getExpR().visit(this, env);
		if(val1 > val2) return True;				
		return False;
	}
									
	Exp LchildRchild = exp.getExpL().getSubTree(1); 
	Exp Rchild = exp.getExpR();
	
	val1 = exp.getExpL().visit(this, env);
	if (val1 != True) return False;
	
	val2 = LchildRchild.visit(this,env);
	val3 = Rchild.visit(this,env);
	if (val2 > val3) return True;
	return False;

    }

    public Double visitExpGreaterThanEqual(ExpGreaterThanEqual exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2, val3;
	
	// base case
	if (exp.getExpL().getSubTrees().size() == 0 || (exp.getExpL().getSubTree(0).getSubTrees().size() == 0 && !(exp.getExpL().getName().equals(">=")))){
		val1 = exp.getExpL().visit(this, env);
		val2 = exp.getExpR().visit(this, env);
		if(val1 >= val2) return True;				
		return False;
	}
									
	Exp LchildRchild = exp.getExpL().getSubTree(1); 
	Exp Rchild = exp.getExpR();
	
	val1 = exp.getExpL().visit(this, env);
	if (val1 != True) return False;
	
	val2 = LchildRchild.visit(this,env);
	val3 = Rchild.visit(this,env);
	if (val2 >= val3) return True;
	return False;

    }

    public Double visitExpEqual(ExpEqual exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2, val3;
	
	// base case
	if (exp.getExpL().getSubTrees().size() == 0 || !(exp.getExpL().getName().equals("=="))){
		val1 = exp.getExpL().visit(this, env);
		val2 = exp.getExpR().visit(this, env);
		if(val1.equals(val2)) return True;				
		return False;
	}
									
	Exp LchildRchild = exp.getExpL().getSubTree(1); 
	Exp Rchild = exp.getExpR();
	
	val1 = exp.getExpL().visit(this, env);
	if (val1 != True) return False;
	
	val2 = LchildRchild.visit(this,env);
	val3 = Rchild.visit(this,env);
	if (val2.equals(val3)) return True;
	return False;

    }

    public Double visitExpNotEqual(ExpNotEqual exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2, val3;
	
	// base case
	if (exp.getExpL().getSubTrees().size() == 0 || !(exp.getExpL().getName().equals("!="))){
		val1 = exp.getExpL().visit(this, env);
		val2 = exp.getExpR().visit(this, env);
		if(!(val1.equals(val2))) return True;				
		return False;
	}
									
	Exp LchildRchild = exp.getExpL().getSubTree(1); 
	Exp Rchild = exp.getExpR();
	
	val1 = exp.getExpL().visit(this, env);
	if (val1 != True) return False;
	
	val2 = LchildRchild.visit(this,env);
	val3 = Rchild.visit(this,env);
	if (!(val2.equals(val3))) return True;
	return False;

    }



    public Double visitExpNot(ExpNot exp, Environment<Double> env)
	throws VisitException {
	Double val1;
	val1 = exp.getExp().visit(this, env);
	if(val1 == -10) return True;
	return False;
    }

    public Double visitExpAnd(ExpAnd exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	if(val1 != True) return val1;
	val2 = exp.getExpR().visit(this, env);
	if(val2 != True) return val2;
	return True;
    }

    public Double visitExpOr(ExpOr exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	if(val1 == True) return True;
	val2 = exp.getExpR().visit(this, env);
	if(val2 == True) return True;
	return False;
    }

    public Double visitExpComparator(ExpComparator exp, Environment<Double> env)
	throws VisitException {
	return False;
    }

}
