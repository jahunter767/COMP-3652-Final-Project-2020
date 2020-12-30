import java.util.*;

public class Evaluator implements Visitor<Environment<SMPLObject>, SMPLObject> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation.*/

    // allocate state here
    protected SMPLObject result;	// result of evaluation
    private SMPLObject defaultValue;
    private Class<SMPLObject> myClass;

    protected Evaluator(){
	this(SMPL.makeInstance(null));
    }

    public Evaluator(SMPLObject defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = SMPLObject.class;
	result = defaultValue;
    }

    public Environment<SMPLObject> getDefaultState() {
	return Environment.makeGlobalEnv(myClass);
    }

    public SMPLObject visitArithProgram(ArithProgram p, Environment<SMPLObject> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
    }


    public SMPLObject visitSubstr(Substr exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1, arg2, arg3;
	arg1 = exp.getArg1().visit(this, env);
	arg2 = exp.getArg2().visit(this, env);
	arg3 = exp.getArg3().visit(this, env);
	return arg1.substr(arg2,arg3);
    }


    public SMPLObject visitStatement(Statement s, Environment<SMPLObject> env)
    throws VisitException {
	return s.getExp().visit(this, env);
    }

    public SMPLObject visitStmtSequence(StmtSequence sseq, Environment<SMPLObject> env)
	throws VisitException {
	// remember that env is the environment
	Statement s;
	ArrayList seq = sseq.getSeq();
	Iterator iter = seq.iterator();
	SMPLObject result = defaultValue;
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, env);
	}
	// return last value evaluated
	return result;
    }

    public SMPLObject visitStmtDefinition(StmtDefinition sd,
				      Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject result;
	result = sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return SMPL.makeInstance(null);
    }

	public SMPLObject visitStmtAssignment(StmtAssignment sa,
				      Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject result;
	result = sa.getExp().visit(this, env);
	env.update(sa.getVar(), result);
	return SMPL.makeInstance(null);
    }


	public SMPLObject visitStmtPrint(StmtPrint p, Environment<SMPLObject> env)
	throws VisitException{
	System.out.print(p.getExp().visit(this, env));
	return SMPL.makeInstance(null);
	}

	public SMPLObject visitStmtPrintln(StmtPrintln p, Environment<SMPLObject> env)
	throws VisitException{
	System.out.println(p.getExp().visit(this, env));
	return SMPL.makeInstance(null);
	}


    public SMPLObject visitStmtFunDefn(StmtFunDefn fd, Environment<SMPLObject> env)
	throws VisitException {
	Closure closure = new Closure(fd,env);// wrap function in a closure
	SMPLObject f = SMPL.makeInstance(closure); //wrap that closure in SMPLFunc 
	// to be implemented
	return f; // return that SMPLFunc
    }

    public SMPLObject visitExpFunCall(ExpFunCall fc, Environment<SMPLObject> env)
	throws VisitException, MismatchedParamsException {
	SMPLFunction Func = (SMPLFunction) fc.getFunction().visit(this, env);
	Closure c = Func.getClosure();
	StmtFunDefn myFunc = c.getFunction();
	ArrayList<String> params = myFunc.getParams(); // getParam is within the function defintion
	String paramOvf = myFunc.getParamOvf();
	int paramCount = params.size();

	ArrayList<Exp> arguements = fc.getArgs(); // expressions that we got as arguments
	int argCount = arguements.size();
	
	if (((paramCount < argCount) && (paramOvf == null)) ||
	(paramCount > argCount)){
		throw new MismatchedParamsException(params.size(),arguements.size());
	}

	ArrayList<SMPLObject> args = new ArrayList<SMPLObject>();
	Environment newEnv;
	int i;
	for(i = 0; i < paramCount; i++){
		args.add(arguements.get(i).visit(this, env));
	}

	newEnv = new Environment(c.getClosingEnv(), params, args);
	if (paramOvf != null){
		ArrayList<SMPLObject> argOvf = new ArrayList<SMPLObject>();
		for (i = paramCount; i < argCount; i++){
			argOvf.add(arguements.get(i).visit(this, env));
		}
		newEnv.put(paramOvf, SMPL.makeInstance(new List(argOvf)));
	}

	return myFunc.getBody().visit(this, newEnv);
    }

	public SMPLObject visitExpCall(ExpCall fc, Environment<SMPLObject> env)
	throws VisitException, MismatchedParamsException {
	SMPLFunction Func = (SMPLFunction) fc.getFunction().visit(this, env);
	Closure c = Func.getClosure();
	StmtFunDefn myFunc = c.getFunction();
	ArrayList<String> params = myFunc.getParams(); // getParam is within the function defintion
	String paramOvf = myFunc.getParamOvf();
	int paramCount = params.size();

	ArrayList<SMPLObject> arguements = new ArrayList<SMPLObject>();
	SMPLPair lst = (SMPLPair) fc.getArgs().visit(this, env); // expressions that we got as arguments
	SMPLObject left = lst.car();
	SMPLObject next = lst.cdr();
	SMPLBoolean p = (SMPLBoolean) next.equalTo(new SMPLNil());
	while (!p.getVal()){
		arguements.add(left);
		left = next.car();
		next = next.cdr();
		p = (SMPLBoolean) next.equalTo(new SMPLNil());
	}
	arguements.add(left);
	int argCount = arguements.size();

	if (((paramCount < argCount) && (paramOvf == null)) ||
	(paramCount > argCount)){
		throw new MismatchedParamsException(params.size(),arguements.size());
	}

	ArrayList<SMPLObject> args = new ArrayList<SMPLObject>();
	Environment newEnv;
	int i;
	for(i = 0; i < paramCount; i++){
		args.add(arguements.get(i));
	}

	newEnv = new Environment(c.getClosingEnv(), params, args);
	if (paramOvf != null){
		ArrayList<SMPLObject> argOvf = new ArrayList<SMPLObject>();
		for (i = paramCount; i < argCount; i++){
			argOvf.add(arguements.get(i));
		}
		newEnv.put(paramOvf, SMPL.makeInstance(new List(argOvf)));
	}

	return myFunc.getBody().visit(this, newEnv);
    }


	public SMPLObject visitExpIf(ExpIf ifStmt, Environment<SMPLObject> env)
	throws VisitException {
	SMPLBoolean pred = (SMPLBoolean) ifStmt.getPredicate().visit(this, env);
	SMPLObject result;
	
	if (pred.getVal()){
		result = ifStmt.getConsequent().visit(this, env);
	}else{
		result = ifStmt.getAlternative().visit(this, env);
	}
	return result;
    }

	public SMPLObject visitExpCase(ExpCase c, Environment<SMPLObject> env)
	throws VisitException {
	ArrayList<Exp> clauses = c.getClauses();
	SMPLBoolean pred;
	ExpClause cl;
	for (Exp clause: clauses){
		cl = (ExpClause) clause;
		pred = (SMPLBoolean) cl.getPredicate().visit(this, env);
		if (pred.getVal()){
			return cl.visit(this, env);
		}
	}
	return SMPL.makeInstance(null);
    }

	public SMPLObject visitExpClause(ExpClause c, Environment<SMPLObject> env)
	throws VisitException {
	return c.getConsequent().visit(this, env);
    }


	public SMPLObject visitExpLet(ExpLet l, Environment<SMPLObject> env)
	throws VisitException{
	ArrayList<ExpBind> bindLst = l.getBindings();
	ArrayList<String> ids = new ArrayList<String>();
	ArrayList<SMPLObject> values = new ArrayList<SMPLObject>();
	for (ExpBind b: bindLst){
		ids.add(b.getName());
		values.add(b.visit(this, env));
	}
	Environment<SMPLObject> newEnv = new Environment<SMPLObject>(env, ids, values);
	return l.getBody().visit(this, newEnv);
	}

	public SMPLObject visitExpBind(ExpBind b, Environment<SMPLObject> env)
	throws VisitException{
	return b.getExpr().visit(this, env);
	}


	public SMPLObject visitExpRead(ExpRead r, Environment<SMPLObject> env)
	throws VisitException{
	Scanner sc = new Scanner(System.in);
	return SMPL.makeInstance(sc.next());
	}

	public SMPLObject visitExpReadInt(ExpReadInt r, Environment<SMPLObject> env)
	throws VisitException{
	Scanner sc = new Scanner(System.in);
	return SMPL.makeInstance(new Double(sc.nextInt()));
	}


    public SMPLObject visitExpPair(ExpPair exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject obj1, obj2;
	obj1 = exp.getExpL().visit(this, env);
	obj2 = exp.getExpR().visit(this, env);
	return SMPL.makeInstance(new Pair(obj1,obj2));
    }

    public SMPLObject visitCar(Car exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1;
	arg1 = exp.getArg1().visit(this, env);
	return arg1.car();
    }

    public SMPLObject visitCdr(Cdr exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1;
	arg1 = exp.getArg1().visit(this, env);
	return arg1.cdr();
    }

    public SMPLObject visitisPair(isPair exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg;
	arg = exp.getArg1().visit(this, env);
	return SMPL.makeInstance(arg instanceof SMPLPair);
    }


    public SMPLObject visitExpList(ExpList lst, Environment<SMPLObject> env)
	throws VisitException{
	ArrayList<SMPLObject> args = new ArrayList<>();
	ArrayList<Exp> exp = lst.getArgs(); // expressions that we got as arguments
	if(exp.size() <= 0) return SMPL.makeInstance(new Nil());	
	for(int i = 0; i < exp.size(); i++){
		args.add(exp.get(i).visit(this, env));
	}
	return SMPL.makeInstance(new List(args));
    }


    public SMPLObject visitExpVector(ExpVector vect, Environment<SMPLObject> env)
	throws VisitException{
	ArrayList<SMPLObject> args = new ArrayList<>();
	ArrayList<Exp> exp = vect.getVal();	
	for(int i = 0; i < exp.size(); i++){
		args.add(exp.get(i).visit(this, env));
	}
	return SMPL.makeInstance(new Vector(args));
    }

	public SMPLObject visitSize(Size exp, Environment<SMPLObject> env)
	throws VisitException{
	SMPLVector arg = (SMPLVector) exp.getArg1().visit(this, env);
	return arg.size();
    }

	public SMPLObject visitExpGetVectEl(ExpGetVectEl exp, Environment<SMPLObject> env)
	throws VisitException{
	SMPLVector vect;
	SMPLObject index;
	vect = (SMPLVector) exp.getVect().visit(this, env);
	index = exp.getIndex().visit(this, env);
	return vect.get(index);
    }

	public SMPLObject visitExpSetVectEl(ExpSetVectEl exp, Environment<SMPLObject> env)
	throws VisitException{
	SMPLVector vect;
	SMPLObject index, val;
	vect = (SMPLVector) exp.getVect().visit(this, env);
	index = exp.getIndex().visit(this, env);
	val = exp.getValue().visit(this, env);
	return vect.set(index, val);
    }


	public SMPLObject visitEqual(Equal exp, Environment<SMPLObject> env)
	throws VisitException{
	SMPLObject val1, val2;
	val1 = exp.getArg1().visit(this, env);
	val2 = exp.getArg2().visit(this, env);
	return val1.equalTo(val2);
    }

	public SMPLObject visitEqv(Eqv exp, Environment<SMPLObject> env)
	throws VisitException{
	SMPLObject val1, val2;
	val1 = exp.getArg1().visit(this, env);
	val2 = exp.getArg2().visit(this, env);
	return val1.eqv(val2);
    }


    public SMPLObject visitExpBitwiseNot(ExpBitwiseNot exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1;
	val1 = exp.getPredicate().visit(this, env);
	return val1.bitwiseNot();
    }

    public SMPLObject visitExpBitwiseAnd(ExpBitwiseAnd exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.bitwiseAnd(val2);
    }

    public SMPLObject visitExpBitwiseOr(ExpBitwiseOr exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.bitwiseOr(val2);
    }


	public SMPLObject visitExpLess(ExpLess exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.lessThan(val2);
    }

	public SMPLObject visitExpLessEq(ExpLessEq exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.lessThanEq(val2);
    }

	public SMPLObject visitExpEqual(ExpEqual exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.equalTo(val2);
    }

    public SMPLObject visitExpGreaterEq(ExpGreaterEq exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.greaterThanEq(val2);
    }

    public SMPLObject visitExpGreater(ExpGreater exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.greaterThan(val2);
    }

	public SMPLObject visitExpNotEqual(ExpNotEqual exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.notEqualTo(val2);
    }


	public SMPLObject visitExpNot(ExpNot exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1;
	val1 = exp.getPredicate().visit(this, env);
	return val1.not();
    }

    public SMPLObject visitExpAnd(ExpAnd exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.and(val2);
    }

    public SMPLObject visitExpOr(ExpOr exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.or(val2);
    }


	public SMPLObject visitExpAdd(ExpAdd exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.add(val2);
    }

    public SMPLObject visitExpSub(ExpSub exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.subtract(val2);
    }

    public SMPLObject visitExpMul(ExpMul exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.multiply(val2);
    }

    public SMPLObject visitExpDiv(ExpDiv exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.divide(val2);
    }

    public SMPLObject visitExpMod(ExpMod exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.mod(val2);
    }

    public SMPLObject visitExpPow(ExpPow exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.pow(val2);
    }


    public SMPLObject visitExpLit(ExpLit exp, Environment<SMPLObject> env)
	throws VisitException {
	return SMPL.makeInstance(exp.getVal()); // returns the SMPL object
    }

    public SMPLObject visitExpVar(ExpVar exp, Environment<SMPLObject> env)
	throws VisitException {
	return env.get(exp.getVar());
    }

}
