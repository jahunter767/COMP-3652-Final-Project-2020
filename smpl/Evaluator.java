import java.util.*;

public class Evaluator implements Visitor<Environment<Object>, Object> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. */

    // allocate state here
    protected Object result;	// result of evaluation
    private Double defaultValue;
    private Class<Object> myClass;

    protected Evaluator() {
	this(Double.NaN);
    }

    public Evaluator(Double defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = Object.class;
	result = defaultValue;
    }

    public Environment<Object> getDefaultState() {
	return Environment.makeGlobalEnv(myClass);
    }

    public Object visitArithProgram(ArithProgram p, Environment<Object> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
    }

    public Object visitStatement(Statement s, Environment<Object> env)
    throws VisitException {
	return s.getExp().visit(this, env);
    }

    public Object visitStmtSequence(StmtSequence sseq, Environment<Object> env)
	throws VisitException {
	// remember that env is the environment
	Statement s;
	ArrayList seq = sseq.getSeq();
	Iterator iter = seq.iterator();
	Object result = defaultValue;
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, env);
	}
	// return last value evaluated
	return result;
    }

    public Object visitStmtDefinition(StmtDefinition sd,
				      Environment<Object> env)
	throws VisitException {
	Double result;
	result = (Double) sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return result;
    }

    public Object visitStmtFunDefn(StmtFunDefn fd, Environment<Object> env)
	throws VisitException {
	Closure c = new Closure(fd, env);
	env.put(fd.getFname(), c); //Add function defintion binding to environment
	return 0D; 
    }

    public Object visitExpFunCall(ExpFunCall fc, Environment<Object> env)
	throws VisitException {
	// to be implemented
	StmtFunDefn function;
    Environment<Object> closingEnv;
	StmtSequence sseq;  //To be evaulated
	ArrayList<Object> params,args;
	//Extract function from closure object : Env Lookup
	Closure c = (Closure) env.get(fc.getVar());
	function = c.getFunction();
	closingEnv = env;
	//Make bindings for function call arguements
	sseq = function.getStmt();
	params = function.getExps();
	args = fc.getExp();
	ExpVar var;
	ExpLit expLit;
	for (int i = 0; i < params.size(); i++){
	var = (ExpVar) params.get(i);
	expLit = (ExpLit) args.get(i);
	closingEnv.put(var.getVar(), expLit.visit(this, env));	
	}
	//Evaulate function body with arg bindings
	result = (Double) sseq.visit(this,closingEnv);
	return result;
    }

    public Object visitExpAdd(ExpAdd exp, Environment<Object> env)
	throws VisitException {
	Double val1, val2;
	val1 = (Double) exp.getExpL().visit(this, env);
	val2 = (Double) exp.getExpR().visit(this, env);
	return val1 + val2;
    }

    public Object visitExpSub(ExpSub exp, Environment<Object> env)
	throws VisitException {
	Double val1, val2;
	val1 = (Double) exp.getExpL().visit(this, env);
	val2 = (Double) exp.getExpR().visit(this, env);
	return val1 - val2;
    }

    public Object visitExpMul(ExpMul exp, Environment<Object> env)
	throws VisitException {
	Double val1, val2;
	val1 = (Double) exp.getExpL().visit(this, env);
	val2 = (Double) exp.getExpR().visit(this, env);
	return val1 * val2;
    }

    public Object visitExpDiv(ExpDiv exp, Environment<Object> env)
	throws VisitException {
	Double val1, val2;
	val1 = (Double) exp.getExpL().visit(this, env);
	val2 = (Double) exp.getExpR().visit(this, env);
	return val1 / val2;
    }

    public Object visitExpMod(ExpMod exp, Environment<Object> env)
	throws VisitException {
	Double val1, val2;
	val1 = (Double) exp.getExpL().visit(this, env);
	val2 = (Double) exp.getExpR().visit(this, env);
	return val1 % val2;
    }

    public Object visitExpLit(ExpLit exp, Environment<Object> env)
	throws VisitException {
	return exp.getVal();
    }

    public Object visitExpVar(ExpVar exp, Environment<Object> env)
	throws VisitException {
	return (Double) env.get(exp.getVar());
    }
	
	public Object visitCompareL(CompareL exp,Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	Boolean r;
	if (val2 instanceof Boolean){
		Double val3 = (Double) CompareL.nextVal.visit(this,env);
		//System.out.println(val1 + " " + val3 );
		r = (Double) val1 < val3;
		r = r & (Boolean) val2;
	}else {
		//System.out.println(val1 + " " + val2 );
		r = (Double) val1 < (Double) val2;
	}
	CompareL.nextVal = exp.getExpL();
	return r;
	}

	public Object visitCompareLE(CompareLE exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	Boolean r;
	if (val2 instanceof Boolean){
		Double val3 = (Double) CompareLE.nextVal.visit(this,env);
		//System.out.println(val1 + " " + val3 );
		r = (Double) val1 <= val3;
		r = r & (Boolean) val2;
	}else {
		//System.out.println(val1 + " " + val2 );
		r = (Double) val1 <= (Double) val2;
	}
	CompareLE.nextVal = exp.getExpL();
	return r;
	}
	
	
	public Object visitCompareG(CompareG exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	Boolean r;
	if (val2 instanceof Boolean){
		Double val3 = (Double) CompareG.nextVal.visit(this,env);
		//System.out.println(val1 + " " + val3 );
		r = (Double) val1 > val3;
		r = r & (Boolean) val2;
	}else {
		//System.out.println(val1 + " " + val2 );
		r = (Double) val1 > (Double) val2; 
	}
	CompareG.nextVal = exp.getExpL();
	return r;
	}
	
	public Object visitCompareGE(CompareGE exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	Boolean r;
	if (val2 instanceof Boolean){
		Double val3 = (Double) CompareGE.nextVal.visit(this,env);
		//System.out.println(val1 + " " + val3 );
		r = (Double) val1 >= val3;
		r = r & (Boolean) val2;
	}else {
		//System.out.println(val1 + " " + val2 );
		r = (Double) val1 >= (Double) val2; 
	}
	CompareGE.nextVal = exp.getExpL();
	return r;
	}
	
	public Object visitStmtAnd(StmtAnd exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	Boolean flag = true;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	flag = flag & (Boolean) val1 & (Boolean) val2;
	return flag;
	}
	
	public Object visitStmtOr(StmtOr exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	Boolean flag = false;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	flag = flag | (Boolean) val1 | (Boolean) val2;
	return flag;
	}
	
	public Object visitStmtNot(StmtNot exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	val1 = (Object) exp.getExpL().visit(this, env);
	if ((Boolean) val1 == true){
		return false;
	}else {
		return true;
	}
	}
	
	public Object visitStmtIfDefn(StmtIfDefn exp, Environment<Object> env) 
	throws VisitException {
	Exp p = exp.getPredicate();
	StmtSequence c = exp.getConsequent();
	Statement a = exp.getAlternative();
	Boolean flag = (Boolean) p.visit(this, env);
	if (flag == true){
		result = (Double) c.visit(this,env);
	}else{
		result = (Double) a.visit(this,env);
	}
	
	return result;
	}
	
	
}
