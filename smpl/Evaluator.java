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
	this(SMPL.makeInstance(new Integer(-99)));
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
	SMPLFunction Func = env.get(fc.getName());
	Closure c = Func.getClosure();

	/*
	SMPLFunction Func = null;
	if(fc.getProcedure() != null){Func = (SMPLFunction)fc.getProcedure().visit(this,env);}
	else if(fc.getName() != null){Func = (SMPLFunction)env.get(fc.getName());}
	else {throw new VisitException("Error: Unknown function.");}
	*/
	
	ArrayList<SMPLObject> args = new ArrayList<SMPLObject>();
	ArrayList<Exp> exp = fc.getArgs(); // expressions that we got as arguments
	StmtFunDefn myFunc = Func.getClosure().getFunction();
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


    public SMPLObject visitSubstr(Substr exp, Environment<SMPLObject> env)
	throws VisitException {
	SMPLObject arg1, arg2, arg3;
	arg1 = exp.getArg1().visit(this, env);
	arg2 = exp.getArg2().visit(this, env);
	arg3 = exp.getArg3().visit(this, env);
	return arg1.Substr(arg2,arg3);
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
