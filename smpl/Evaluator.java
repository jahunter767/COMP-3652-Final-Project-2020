import java.util.*;

public class Evaluator implements Visitor<Environment<ExpLit>, ExpLit> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. */

    // allocate state here
    protected ExpLit result;	// result of evaluation
    private ExpLit defaultValue;
    private Class<ExpLit> myClass;

    protected Evaluator() {
	this(new SmplNone()));
    }

    public Evaluator(ExpLit defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = ExpLit.class;
	result = defaultValue;
    }

    public Environment<ExpLit> getDefaultState() {
	return Environment.makeGlobalEnv(myClass);
    }

    public ExpLit visitArithProgram(ArithProgram p, Environment<ExpLit> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
    }

    public ExpLit visitStatement(Statement s, Environment<ExpLit> env)
    throws VisitException {
	return s.getExp().visit(this, env);
    }

    public ExpLit visitStmtSequence(StmtSequence sseq, Environment<ExpLit> env)
	throws VisitException {
	// remember that env is the environment
	Statement s;
	ArrayList seq = sseq.getSeq();
	Iterator iter = seq.iterator();
	ExpLit result = defaultValue;
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, env);
	}
	// return last value evaluated
	return result;
    }

    public ExpLit visitStmtDefinition(StmtDefinition sd, Environment<ExpLit> env)
	throws VisitException {
	ExpLit result;
	result = sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return result;
    }

    public ExpLit visitStmtFunDefn(StmtFunDefn fd, Environment<ExpLit> env)
	throws VisitException {
	Closure c = new Closure(fd, env);
	env.putClosure(fd.getName(), c);
	return new SmplNone();
    }

    public ExpLit visitExpFunCall(ExpFunCall fc, Environment<ExpLit> env)
	throws VisitException {
	Closure c = env.getClosure(fc.getName());
	StmtFunDefn funDef = c.getFunction();

	ArrayList<Exp> args = fc.getArgs();
	ArrayList<ExpLit> values = new ArrayList<ExpLit>();
	for (Exp arg : args){
		values.add(arg.visit(this, env));
	}

	ArrayList<String> params = funDef.getParameters();
	Environment<ExpLit> subEnv = new Environment<ExpLit>(c.getClosingEnv(), params, values);
	Exp body = funDef.getBody();
	return body.visit(this, subEnv);
	}

	public ExpLit visitExpIf(ExpIf ifStmt, Environment<ExpLit> env)
	throws VisitException {
	SmplBool decision = ifStmt.getPredicate().visit(this, env);
	if (decision.getVal()){
		return ifStmt.getAlternative().visit(this, env);
	}else{
		return ifStmt.getConsequent().visit(this, env);
	}
    }

    public ExpLit visitExpAdd(ExpAdd exp, Environment<ExpLit> env)
	throws VisitException {
	ExpLit val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	if ((val1 instanceof SmplDouble) || (val2 instanceof SmplDouble)){
		return new SmplDouble(val1.getVal() + val2.getVal());
	}
	return new SmplInt(val1.getVal() + val2.getVal());
    }

    public ExpLit visitExpSub(ExpSub exp, Environment<ExpLit> env)
	throws VisitException {
	ExpLit val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	if ((val1 instanceof SmplDouble) || (val2 instanceof SmplDouble)){
		return new SmplDouble(val1.getVal() - val2.getVal());
	}
	return new SmplInt(val1.getVal() - val2.getVal());
    }

    public ExpLit visitExpMul(ExpMul exp, Environment<ExpLit> env)
	throws VisitException {
	ExpLit val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	if ((val1 instanceof SmplDouble) || (val2 instanceof SmplDouble)){
		return new SmplDouble(val1.getVal() * val2.getVal());
	}
	return new SmplInt(val1.getVal() * val2.getVal());
    }

    public ExpLit visitExpDiv(ExpDiv exp, Environment<ExpLit> env)
	throws VisitException {
	ExpLit val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	if ((val1 instanceof SmplDouble) || (val2 instanceof SmplDouble)){
		return new SmplDouble(val1.getVal() / val2.getVal());
	}
	return new SmplInt(val1.getVal() / val2.getVal());
    }

    public ExpLit visitExpMod(ExpMod exp, Environment<ExpLit> env)
	throws VisitException {
	ExpLit val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	if ((val1 instanceof SmplDouble) || (val2 instanceof SmplDouble)){
		return new SmplDouble(val1.getVal() % val2.getVal());
	}
	return new SmplInt(val1.getVal() % val2.getVal());
    }

	public ExpLit visitExpAnd(ExpAnd exp, Environment<ExpLit> env)
	throws VisitException {
	return exp.getExpL().visit(this, env).getVal() &&
		exp.getExpR().visit(this, env).getVal();
	}

	public ExpLit visitExpOr(ExpOr exp, Environment<ExpLit> env)
	throws VisitException {
	return exp.getExpL().visit(this, env).getVal() ||
		exp.getExpR().visit(this, env).getVal();
	}

	public ExpLit visitExpNot(ExpNot exp, Environment<ExpLit> env)
	throws VisitException {
	SmplBool val;
	val = exp.getPredicate().visit(this, env);
	return new SmplBool(!val.getVal());
	}

	public ExpLit visitExpLess(ExpLess exp, Environment<ExpLit> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpLit r = left.getRightPred();
		return new SmplBool(left.visit(this, env).getVal() &&
			(r.getVal() < right.getVal()));
	}
	return new SmplBool(left.getVal() < right.getVal());
	}

	public ExpLit visitExpLessEq(ExpLessEq exp, Environment<ExpLit> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpLit r = left.getRightPred();
		return new SmplBool(left.visit(this, env).getVal() &&
			(r.getVal() <= right.getVal()));
	}
	return new SmplBool(left.getVal() <= right.getVal());
	}

	public ExpLit visitExpEqual(ExpEqual exp, Environment<ExpLit> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpLit r = left.getRightPred();
		return new SmplBool(left.visit(this, env).getVal() &&
			(r.getVal() == right.getVal()));
	}
	return new SmplBool(left.getVal() == right.getVal());
	}

	public ExpLit visitExpGreaterEq(ExpGreaterEq exp, Environment<ExpLit> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpLit r = left.getRightPred();
		return new SmplBool(left.visit(this, env).getVal() &&
			(r.getVal() >= right.getVal()));
	}
	return new SmplBool(left.getVal() >= right.getVal());
	}

	public ExpLit visitExpGreater(ExpGreater exp, Environment<ExpLit> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpLit r = left.getRightPred();
		return new SmplBool(left.visit(this, env).getVal() &&
			(r.getVal() > right.getVal()));
	}
	return new SmplBool(left.getVal() > right.getVal());
	}

	public ExpLit visitExpNotEqual(ExpNotEqual exp, Environment<ExpLit> env)
	throws VisitException {
	Exp left = exp.getLeftPred();
	Exp right = exp.getRightPred();
	if (left instanceof ExpBinComp){
		ExpLit r = left.getRightPred();
		return new SmplBool(left.visit(this, env).getVal() &&
			(r.getVal() != right.getVal()));
	}
	return new SmplBool(left.getVal() != right.getVal());
	}


	public ExpLit visitExpVar(ExpVar exp, Environment<ExpLit> env)
	throws VisitException {
	return env.get(exp.getVar());
	}


	public ExpLit visitSmplInt(SmplInt exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
	
	public ExpLit visitSmplDouble(SmplDouble exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
	
	public ExpLit visitSmplString(SmplString exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
	
	public ExpLit visitSmplChar(SmplChar exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
	
	public ExpLit visitSmplBool(SmplBool exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
	
	public ExpLit visitSmplPair(SmplPair exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
	
	public ExpLit visitSmplVector(SmplVector exp, Environment<ExpLit> arg)
	throws VisitException{
	return exp;
	}
}
