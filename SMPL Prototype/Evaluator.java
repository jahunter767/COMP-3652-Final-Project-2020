import java.util.*;

public class Evaluator implements Visitor<Environment<SMPLObject>, SMPLObject> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. -- new vers -- */

    // allocate state here
    protected SMPLObject result;	// result of evaluation
    private SMPLObject defaultValue;
    private Class<SMPLObject> myClass;
    //private SMPLObject True;
    //private SMPLObject False;

    protected Evaluator(){
	this(SMPL.makeInstance(new Integer(-99)));
    }

    public Evaluator(SMPLObject defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = SMPLObject.class;
	result = defaultValue;
	//True = 0D;
	//False = -10D;
    }

    public Environment<SMPLObject> getDefaultState() {
	return Environment.makeGlobalEnv(myClass);
    }

    public SMPLObject visitArithProgram(ArithProgram p, Environment<SMPLObject> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
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


//------ FROM JASON's CODE---------

	public SMPLObject visitStmtPrint(StmtPrint p, Environment<SMPLObject> env)
	throws VisitException{
	System.out.print(p.getExp().visit(this, env));
	return SMPL.makeInstance();
	}

	public SMPLObject visitStmtPrintln(StmtPrintln p, Environment<SMPLObject> env)
	throws VisitException{
	System.out.println(p.getExp().visit(this, env));
	return SMPL.makeInstance();
	}







//--------------------------------

    public SMPLObject visitStmtDefinition(StmtDefinition sd,
				      Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject result;
	result = sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return result;
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
	SMPLFunction Func = null;
	if(fc.getProcedure() != null){Func = (SMPLFunction)fc.getProcedure().visit(this,env);}
	else if(fc.getName() != null){Func = (SMPLFunction)env.get(fc.getName());}
	else {throw new VisitException("Error: Unknown function.");}

	ArrayList<SMPLObject> args = new ArrayList<>();
	ArrayList<Exp> exp = fc.getArgs(); // expressions that we got as arguments
	StmtFunDefn myFunc = Func.getVal().getFunction();// getVal is the closure within SMPLFunction. The funcdef is within closure
	ArrayList<String> parameters = myFunc.getParams(); // getParam is within the function defintion 

	if (parameters.size() != exp.size()){
		throw new MismatchedParamsException(parameters.size(),exp.size());
	}
	
	for(int i = 0; i < parameters.size(); i++){
		args.add(exp.get(i).visit(this,env));// bounding the arguments to variables 
		
	}
	
	Environment newEnv = new Environment(Func.getVal().getClosingEnv(),parameters,args);
	
	return myFunc.getBody().visit(this,newEnv);
    }



    public SMPLObject visitExpAdd(ExpAdd exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1.add(val2);
    }


// ------ FROM JASON'S CODE ------
/*
	public SMPLObject visitExpBind(ExpBind b, Environment<SMPLObject> env)
	throws VisitException{
	return b.getExpr().visit(this, env);
	}

*/

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
	return SMPL.makeInstance();
    }

	public SMPLObject visitExpClause(ExpClause c, Environment<SMPLObject> env)
	throws VisitException {
	return c.getConsequent().visit(this, env);
    }


	public SMPLObject visitExpRead(ExpRead r, Environment<SMPLObject> env)
	throws VisitException{
	Scanner sc = new Scanner(System.in);
	return SMPL.makeInstance(sc.next());
	}

	public SMPLObject visitExpReadInt(ExpReadInt r, Environment<SMPLObject> env)
	throws VisitException{
	Scanner sc = new Scanner(System.in);
	return SMPL.makeInstance(sc.nextInt());
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




//------------------------


    public SMPLObject visitSubstr(Substr exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1, arg2, arg3;
	arg1 = exp.getArg1().visit(this, env);
	arg2 = exp.getArg2().visit(this, env);
	arg3 = exp.getArg3().visit(this, env);
	return arg1.Substr(arg2,arg3);
    }


    public SMPLObject visitEqv(Eqv exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1, arg2;
	arg1 = exp.getArg1().visit(this, env);
	arg2 = exp.getArg2().visit(this, env);
	return arg1.Eqv(arg2);
    }

    public SMPLObject visitEqual(Equal exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1, arg2;
	arg1 = exp.getArg1().visit(this, env);
	arg2 = exp.getArg2().visit(this, env);
	return arg1.Equal(arg2);
    }


    public SMPLObject visitExpPair(ExpPair exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject obj1, obj2;
	obj1 = exp.getExpL().visit(this, env);
	obj2 = exp.getExpR().visit(this, env);
	return SMPL.makeInstance(new Pair(obj1,obj2));
    }


    public SMPLObject visitExpList(ExpList lst, Environment<SMPLObject> env)
	throws VisitException{


	ArrayList<SMPLObject> args = new ArrayList<>();
	ArrayList<Exp> exp = lst.getArgs(); // expressions that we got as arguments
	
	if(exp.size() <= 0) return SMPL.makeInstance(new Nil());
	
	for(int i = 0; i < exp.size(); i++){
		args.add(exp.get(i).visit(this,env));
	}
	
	return SMPL.makeInstance(new List(args));
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
	SMPLObject arg1;
	arg1 = exp.getArg1().visit(this, env);
	return SMPL.makeInstance(arg1.isType("Pair",arg1.getType()));
    }



    public SMPLObject visitExpLit(ExpLit exp, Environment<SMPLObject> env)
	throws VisitException {
	return SMPL.makeInstance(exp.getVal());// returns the SMPL object / instance//factory methods (eg smplobject.make())
    }


    public SMPLObject visitExpVar(ExpVar exp, Environment<SMPLObject> env)
	throws VisitException {
	return env.get(exp.getVar());
    }


    public SMPLObject visitStringExp(StringExp exp, Environment<SMPLObject> env)
	throws VisitException {
	return SMPL.makeInstance(exp.getString());// returns the SMPL object / instance//factory methods (eg smplobject.make())
    }


}
