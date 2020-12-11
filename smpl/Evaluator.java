import java.util.*;

public class Evaluator implements Visitor<Environment<Double>, Double> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. */

    // allocate state here
    protected Double result;	// result of evaluation
    private Double defaultValue;
    private Class<Double> myClass;

    protected Evaluator() {
	this(Double.NaN);
    }

    public Evaluator(Double defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = Double.class;
	result = defaultValue;
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
	System.out.println("S:" + s.toString());
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
	Closure c = new Closure(fd, env);
	env.putClosure(fd.getName(), c);
	return Double.NaN;
    }

    public Double visitExpFunCall(ExpFunCall fc, Environment<Double> env)
	throws VisitException {
	Closure c = env.getClosure(fc.getName());
	StmtFunDefn funDef = c.getFunction();

	ArrayList<Exp> args = fc.getArgs();
	ArrayList<Double> values = new ArrayList<Double>();
	for (Exp arg : args){
		values.add(arg.visit(this, env));
	}

	ArrayList<String> params = funDef.getParameters();
	Environment<Double> subEnv = new Environment<Double>(c.getClosingEnv(), params, values);
	Exp body = funDef.getBody();
	return body.visit(this, subEnv);
	}

	public Double visitExpIf(ExpIf ifStmt, Environment<Double> env)
	throws VisitException {
	double decision = ifStmt.getPredicate().visit(this, env);
	if (decision == 0.0){
		Double result = ifStmt.getAlternative().visit(this, env);
		if (result == Integer.MAX_VALUE){
			return Double.NaN;
		}
		return result;
	}else{
		return ifStmt.getConsequent().visit(this, env);
	}
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

	public Double visitExpAnd(ExpAnd exp, Environment<Double> env)
	throws VisitException {
	double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	if (val1 == 0.0){
		return new Double(0);
	}else{
		val2 = exp.getExpR().visit(this, env);
		if (val2 == 0.0){
			return new Double(0);
		}else{
			return new Double(1);
		}
	}
	}

	public Double visitExpOr(ExpOr exp, Environment<Double> env)
	throws VisitException {
	double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	if (val1 == 0.0){
		val2 = exp.getExpR().visit(this, env);
		if (val2 == 0.0){
			return new Double(0);
		}else{
			return new Double(1);
		}
	}else{
		return new Double(1);
	}
	}

	public Double visitExpNot(ExpNot exp, Environment<Double> env)
	throws VisitException {
	double val1;
	val1 = exp.getPredicate().visit(this, env);
	if (val1 == 0.0){
		return new Double(1);
	}else{
		return new Double(0);
	}
	}

	public Double visitExpLess(ExpLess exp, Environment<Double> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpLess r = new ExpLess(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, env);
	}else{
		double val1, val2;
		val1 = exp.getLeftPred().visit(this, env).doubleValue();
		val2 = exp.getRightPred().visit(this, env).doubleValue();
		if (val1 < val2){
			return new Double(1);
		}else{
			return new Double(0);
		}
	}
	}

	public Double visitExpLessEq(ExpLessEq exp, Environment<Double> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpLessEq r = new ExpLessEq(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, env);
	}else{
		double val1, val2;
		val1 = exp.getLeftPred().visit(this, env).doubleValue();
		val2 = exp.getRightPred().visit(this, env).doubleValue();
		if (val1 <= val2){
			return new Double(1);
		}else{
			return new Double(0);
		}
	}
	}

	public Double visitExpEqual(ExpEqual exp, Environment<Double> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpEqual r = new ExpEqual(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, env);
	}else{
		double val1, val2;
		val1 = exp.getLeftPred().visit(this, env).doubleValue();
		val2 = exp.getRightPred().visit(this, env).doubleValue();
		System.out.println("Values: " + val1 + " " + val2);
		if (val1 == val2){
			return new Double(1);
		}else{
			return new Double(0);
		}
	}
	}

	public Double visitExpGreaterEq(ExpGreaterEq exp, Environment<Double> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpGreaterEq r = new ExpGreaterEq(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, env);
	}else{
		double val1, val2;
		val1 = exp.getLeftPred().visit(this, env).doubleValue();
		val2 = exp.getRightPred().visit(this, env).doubleValue();
		if (val1 >= val2){
			return new Double(1);
		}else{
			return new Double(0);
		}
	}
	}

	public Double visitExpGreater(ExpGreater exp, Environment<Double> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpGreater r = new ExpGreater(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, env);
	}else{
		double val1, val2;
		val1 = exp.getLeftPred().visit(this, env).doubleValue();
		val2 = exp.getRightPred().visit(this, env).doubleValue();
		if (val1 > val2){
			return new Double(1);
		}else{
			return new Double(0);
		}
	}
	}

	public Double visitExpNotEqual(ExpNotEqual exp, Environment<Double> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpBinComp l = (ExpBinComp) left;
		ExpNotEqual r = new ExpNotEqual(l.getRightPred(), right);
		ExpAnd temp = new ExpAnd(left, r);
		return temp.visit(this, env);
	}else{
		double val1, val2;
		val1 = exp.getLeftPred().visit(this, env).doubleValue();
		val2 = exp.getRightPred().visit(this, env).doubleValue();
		if (val1 != val2){
			return new Double(1);
		}else{
			return new Double(0);
		}
	}
	}

    public Double visitExpLit(ExpLit exp, Environment<Double> env)
	throws VisitException {
	return new Double(exp.getVal());
    }

    public Double visitExpVar(ExpVar exp, Environment<Double> env)
	throws VisitException {
	return env.get(exp.getVar());
    }
}
